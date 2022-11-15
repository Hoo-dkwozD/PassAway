<template>
    <Navbar></Navbar>
    <div class="container align-middle">
        <h4>List of administrators</h4>

        <!-- Button trigger modal -->
        <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal">
            Add new Admin
        </button>

        <!-- Modal -->
        <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-scrollable">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4>List of users</h4>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <table class="table table-hover">
                            <thead>
                                <tr>
                                    <th scope="col">Staff ID</th>
                                    <th scope="col">Email</th>
                                    <th scope="col">Name</th>
                                    <th scope="col">Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr v-for="staff, idx in allStaff">
                                    <template v-if="staff.role != 'ADMINISTRATOR'">
                                        <th scope="row">
                                            {{ staff.staffId }}
                                        </th>
                                        <td>
                                            {{staff.email}}
                                        </td>
                                        <td>
                                            {{ makeFullName(staff.firstName, staff.lastName) }}
                                        </td>
                                        <td>
                                            <button type="button" class="btn btn-primary" :value="staff.staffId" @click="addAdmin(staff.staffId)">Add</button>
                                        </td>
                                    </template>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>

        <table class="table table-hover">
            <thead>
                <tr>
                    <th scope="col">Staff ID</th>
                    <th scope="col">Email</th>
                    <th scope="col">Name</th>
                    <th scope="col">Action</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="admin in adminData">
                    <th scope="row">{{admin.staffId}}</th>
                    <td>{{admin.email}}</td>
                    <td>{{ makeFullName(admin.firstName, admin.lastName) }}</td>
                    <td><button type="button" class="btn btn-danger" @click="removeAdmin(admin.staffId)">Remove</button></td>
                </tr>
            </tbody>
        </table>
    </div>

    <div id="myModal" class="modal fade">
        <div class="modal-dialog modal-confirm">
            <div class="modal-content">
                <div class="modal-header justify-content-center">
                    <div class="icon-box">
                        <i class="material-icons">&#xE876;</i>
                    </div>
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                </div>
                <div class="modal-body text-center">
                    <h4>Great!</h4>	
                    <p>Your account has been created successfully.</p>
                    <button class="btn btn-success" data-dismiss="modal"><span>Start Exploring</span> <i class="material-icons">&#xE5C8;</i></button>
                </div>
            </div>
        </div>
    </div> 
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import axios from "axios";

import Navbar from '../components/Navbar.vue';

// Typings
interface User {
    staffId: number,
    email: string,
    firstName: string,
    lastName: string,
    contactNumber: string,
    role: string,
    cannotBook: boolean,
    disabled: boolean,
    registered: boolean
}

interface AdminViewData {
    allStaff: User[] | null,
    adminData: User[] | null
}

interface LoginData {
    staffId: Number,
    role: String,
}

export default defineComponent({
    components:{
        Navbar
    },
    data(): AdminViewData {
        return{
            adminData: null,
            allStaff : null
        };
    },
    methods:{
        removeAdmin(staffId: number) {
            axios({
                url: import.meta.env.VITE_API_URL + `api/staff/${staffId}/status/borrower`,
                method: 'put',
            })
            .then(res => {
                if (res.data.code == 200) {
                    this.$router.go(0);
                }
            })
            .catch(err => {
                if (err.response) {
                    if (err.response.status == 401) {
                        this.$router.push({ name: "login" }).then(() => {this.$router.go(0);});
                    }
                } else {
                    console.error(err.message);
                }
            })
        },
        addAdmin(staffId: number) {
            axios({
                url: import.meta.env.VITE_API_URL + `api/staff/${staffId}/status/admin`,
                method: 'put',
            })
            .then(res => {
                if (res.data.code == 200) {
                    this.$router.go(0);
                }
            })
            .catch(err => {
                if (err.response) {
                    if (err.response.status == 401) {
                        this.$router.push({ name: "login" }).then(() => {this.$router.go(0);});
                    }
                } else {
                    console.error(err.message);
                }
            })
        },
        makeFullName(firstName: string, lastName: string) {
            return `${firstName} ${lastName}`;
        },
        checkLogin(): LoginData | undefined {
            let staffIdStr = localStorage.getItem("staffId");
            let role = localStorage.getItem("role");

            if (staffIdStr === null || role === null) {
                this.$router.push({ name: 'login' }).then(() => this.$router.go(0));
            } else {
                let staffId = parseInt(staffIdStr);

                return {
                    staffId: staffId,
                    role: role,
                };
            }
        }
    },
    async created(){
        const loginData: LoginData | undefined = this.checkLogin();

        if (loginData === undefined) {
            this.$router.push({ name: 'login' }).then(() => this.$router.go(0));
        } else if (loginData.role !== 'ADMINISTRATOR') {
            this.$router.push({ name: 'home' }).then(() => this.$router.go(0));
        }

        try {
            const adminRes = await axios.get(
                import.meta.env.VITE_API_URL + "api/staffs/status/admin"
            );
            this.adminData = await adminRes.data.data;

            const staffRes = await axios.get(
                import.meta.env.VITE_API_URL + "api/staffs"
            );
            this.allStaff = await staffRes.data.data;
        } catch (err: any) {
            if (err.response) {
                if (err.response.status == 401) {
                    this.$router.push({ name: "login" }).then(() => {this.$router.go(0);});
                }
            } else {
                console.error(err.message);
            }
        }
    }
});
</script>

<style>

</style>
