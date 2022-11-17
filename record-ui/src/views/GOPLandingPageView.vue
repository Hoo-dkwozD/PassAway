<script lang='ts'>
import { defineComponent } from 'vue';
import axios from 'axios';

import Navbar from '../components/Navbar.vue';

// Typings
interface GopLandingPageData {
    loans: any[],
    displayLoans: any[],
    queryString: String | null,
    currentBackground: String | null,
}

interface Loan {
    loanId: Number,
    staffName: String,
    staffEmail: String,
    visitDate: String,
    attractionName: String,
    hasCollected: Boolean,
    hasReturned: Boolean,
    passId: String,
    prevBorrowerName: String,
    prevBorrowerContact: String,
    lost: Boolean
}

export default defineComponent({
    data(): GopLandingPageData {
        return{
            loans: [],
            displayLoans: [],
            queryString: null,
            currentBackground: 'https://img.freepik.com/free-vector/white-desktop-background-modern-minimal-design-vector_53876-140226.jpg?w=1800&t=st=1668366952~exp=1668367552~hmac=a23687ccfe071f6c28017a514a3380e222e62d36894545fc6ff4f9ad24033935'

        }
    },
    components: {
        Navbar
    },
    async created() {
        try {
            const res = await axios.get(
                import.meta.env.VITE_API_URL + `api/loan`,
            );
            const data = await res.data;

            if (data.code === 200) {
                this.loans = await data.data;
                this.loans = this.loans.filter(
                    (loan: Loan) => loan.hasCollected === false || loan.hasReturned === false
                );
                this.displayLoans = this.loans;
            } else {
                console.error(data.message);
            }
        } catch (e) {
            console.error(e);
        }
    },
    methods: {
        filterLoans() {
            if (this.queryString == null) {
                this.queryString = "";
            }

            this.displayLoans = this.loans.filter(
                (loan: any) => loan.staffEmail.toLowerCase().includes(this.queryString?.toLowerCase())
            );
        },
        async markCollected(loan: Loan) {
            try {
                const res = await axios.put(
                    import.meta.env.VITE_API_URL + `api/loan/collect`,
                    {
                        loanId: loan.loanId,
                        hasCollected: true,
                        hasReturned: false
                    }
                )
                const data = await res.data;

                if (data.code !== 200) {
                    console.error(data.message);
                }
            } catch (e) {
                console.error(e);
            }
        },
        async markReturned(loan: Loan) {
            try {
                const res = await axios.put(
                    import.meta.env.VITE_API_URL + `api/loan/return`,
                    {
                        loanId: loan.loanId,
                        hasCollected: true,
                        hasReturned: true
                    }
                )
                const data = await res.data;

                if (data.code !== 200) {
                    console.error(data.message);
                }
            } catch (e) {
                console.error(e);
            }
        },
        async markLost(loan: Loan) {
        }
    }
});
</script>

<template>
    <Navbar></Navbar>
    <div class="content">
        <div class="container-fluid vh-100" :style="{ backgroundImage: `url(${currentBackground})`}">
            <div class="row">
                <div class="col-12">
                    <div class="h-100">
                        <div class="text-center">
                            <router-link to="/">
                                <img src="../assets/SSSlogo.png" class="img mx-auto image-style">
                            </router-link>
                        </div>

                        <div class="borrower-search">
                            <h1 class="h2 text-center">
                                Borrower Information
                            </h1>

                            <div class="form-floating mx-auto mb-3 col-6">
                                <input v-model="queryString" @keyup.enter="filterLoans()" type="email" class="form-control" id="email">
                                <label for="email">Email address</label>
                            </div>
                            <div class="text-center">
                                <button type="button"
                                    class="btn btn-outline-secondary text-uppercase fw-bold search-btn" @click="filterLoans()">Search</button>
                            </div>
                            <div v-if="loans.length !== 0">
                                <div class="bg-light rounded p-2 my-4">
                                    <table class="table table-striped table-hover mt-3">
                                        <thead>
                                            <tr>
                                                <th class="fw-bold" scope="col">Name</th>
                                                <th class="fw-bold" scope="col">Email</th>
                                                <th class="fw-bold" scope="col">Card Number</th>
                                                <th class="fw-bold" scope="col">Loan Date</th>
                                                <th class="fw-bold" scope="col">Attraction</th>
                                                <th class="fw-bold" scope="col">Status</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr v-for="loan, idx in displayLoans" :key="idx">
                                                <td>{{ loan.staffName }}</td>
                                                <td>{{ loan.staffEmail }}</td>
                                                <td>{{ loan.passId }}</td>
                                                <td>{{ loan.visitDate }}</td>
                                                <td>{{ loan.attractionName }}</td>
                                                <td>
                                                    <div class="btn-group" role="group" aria-label="Loan status">
                                                        <input @change="markCollected(loan)" :checked="(!loan.lost) && (!loan.hasReturned) && loan.hasCollected" type="radio" class="btn-check" :name="`loanStatus` + idx" :id="`collected-btn` + idx" autocomplete="off">
                                                        <label class="btn btn-outline-success" :for="`collected-btn` + idx">Collected</label>

                                                        <input @change="markReturned(loan)" :checked="(!loan.lost) && loan.hasReturned" type="radio" class="btn-check" :name="`loanStatus` + idx" :id="`returned-btn` + idx" autocomplete="off">
                                                        <label class="btn btn-outline-info" :for="`returned-btn` + idx">Returned</label>

                                                        <!-- <input @change="markLost(loan)" :checked="loan.lost" type="radio" class="btn-check" name="loanStatus" id="lost-btn" autocomplete="off">
                                                        <label class="btn btn-outline-danger" for="lost-btn">Lost</label> -->
                                                    </div>
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                            <div v-else>
                                <div class="p-5">
                                    <h3>No Loans</h3>
                                    <p class="text-muted">No outstanding/future loans present.</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<style>
.image-style {
    width: 200px;
    height: 200px;
}

.borrower-search {
    text-align: center;
}
</style>
