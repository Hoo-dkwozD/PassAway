<template>
    <Navbar></Navbar>
    <div class="container-fluid">
        <div class="row align-items-stretch">
        <div class="col-12">
            <div class="h-100">
            <div class="text-center">
                <router-link to="/">
                <img
                    src="../assets/SSSlogo.png"
                    class="img mx-auto image-style"
                />
                </router-link>
            </div>

            <h1 class="h2 text-center">View Attraction {{ attractionId }}</h1>

            <div class="form-information">
                <div class="d-flex">
                <div class="row flex-grow-1 mx-2">
                    <div class="align-items">
                    <div class="d-flex flex-grow-1 justify-content-between pb-2 fw-bold">
                        Attraction Name
                    </div>
                    <div>
                        <form>
                        <div class="ms-2">
                            <p class="text-start">{{ attractionName }}</p>
                        </div>
                        </form>
                    </div>
                    </div>
                </div>
                </div>

                <hr class="hr-block" />

                <div class="d-flex">
                <div class="row flex-grow-1 mx-2">
                    <div class="align-items">
                    <div class="d-flex flex-grow-1 justify-content-between pb-2 fw-bold">
                        Description
                    </div>
                    <div>
                        <form>
                        <div class="ms-2">
                            <p class="text-start">{{ description }}</p>
                        </div>
                        </form>
                    </div>
                    </div>
                </div>
                </div>

                <hr class="hr-block" />

                <div class="d-flex">
                <div class="row flex-grow-1 mx-2">
                    <div class="align-items">
                    <div class="d-flex flex-grow-1 justify-content-between pb-2 fw-bold">
                        Address
                    </div>
                    <div>
                        <form>
                        <div class="ms-2">
                            <p class="text-start">{{ address }}</p>
                        </div>
                        </form>
                    </div>
                    </div>
                </div>
                </div>

                <hr class="hr-block" />

                <div class="d-flex">
                <div class="row flex-grow-1 mx-2">
                    <div class="align-items">
                    <div class="d-flex flex-grow-1 justify-content-between pb-2 fw-bold">
                        Membership ID
                    </div>
                    <div>
                        <form>
                        <div class="ms-2">
                            <p class="text-start">{{ membershipID }}</p>
                        </div>
                        </form>
                    </div>
                    </div>
                </div>
                </div>

                <hr class="hr-block" />

                <div class="d-flex">
                <div class="row flex-grow-1 mx-2">
                    <div class="align-items">
                    <div class="d-flex flex-grow-1 justify-content-between pb-2 fw-bold">
                        Expiry Date
                    </div>
                    <div>
                        <form>
                        <div class="ms-2">
                            <p class="text-start">{{ expiryDate }}</p>
                        </div>
                        </form>
                    </div>
                    </div>
                </div>
                </div>

                <hr class="hr-block" />

                <div class="d-flex">
                <div class="row flex-grow-1 mx-2">
                    <div class="align-items">
                    <div class="d-flex flex-grow-1 justify-content-between pb-2 fw-bold">
                        Benefits
                    </div>
                    <div>
                        <form>
                        <div class="ms-2">
                            <p class="text-start">{{ benefits }}</p>
                        </div>
                        </form>
                    </div>
                    </div>
                </div>
                </div>

                <hr class="hr-block" />

                <div class="d-flex">
                <div class="row flex-grow-1 mx-2">
                    <div class="align-items">
                    <div class="d-flex flex-grow-1 justify-content-between pb-2 fw-bold">
                        Terms and Conditions
                    </div>
                    <div>
                        <form>
                        <div class="ms-2">
                            <p class="text-start">{{ termsConditions }}</p>
                        </div>
                        </form>
                    </div>
                    </div>
                </div>
                </div>

                <hr class="hr-block" />

                <div class="d-flex">
                <div class="row flex-grow-1 mx-2">
                    <div class="align-items">
                    <div class="d-flex flex-grow-1 justify-content-between pb-2 fw-bold">
                        Pass Type
                    </div>
                    <div>
                        <form>
                        <div class="ms-2">
                            <p class="text-start">{{ passType }}</p>
                        </div>
                        </form>
                    </div>
                    </div>
                </div>
                </div>
                <hr class="hr-block" />

                <div class="d-flex">
                <div class="row flex-grow-1 mx-2">
                    <div class="align-items">
                    <div class="d-flex flex-grow-1 justify-content-between pb-2 fw-bold">
                        Bookable
                    </div>
                    <div>
                        <form>
                        <div class="ms-2">
                            <p class="text-start">{{ !cannotBook }}</p>
                        </div>
                        </form>
                    </div>
                    </div>
                </div>
                </div>
                <hr class="hr-block" />

                <div class="d-flex">
                <div class="row flex-grow-1 mx-2">
                    <div class="align-items">
                    <div class="d-flex flex-grow-1 justify-content-between pb-2 fw-bold">
                        Replacement Fee
                    </div>
                    <div>
                        <form>
                        <div class="ms-2">
                            <p class="text-start">{{ replacementFee }}</p>
                        </div>
                        </form>
                    </div>
                    </div>
                </div>
                </div>

                <hr class="hr-block" />

                <div class="d-flex">
                <div class="row flex-grow-1 mx-2">
                    <div class="align-items">
                    <div class="d-flex flex-grow-1 justify-content-between pb-2 fw-bold">
                        Maximum Number of Passes
                    </div>
                    <div>
                        <form>
                        <div class="ms-2">
                            <p class="text-start">{{ maximumPasses }}</p>
                        </div>
                        </form>
                    </div>
                    </div>
                </div>
                </div>

                <hr class="hr-block" />

                <div class="d-flex">
                <div class="row flex-grow-1 mx-2">
                    <div class="align-items">
                    <div class="d-flex flex-grow-1 justify-content-between pb-2 fw-bold">
                        Number of Accompanying Guests
                    </div>
                    <div>
                        <form>
                        <div class="ms-2">
                            <p class="text-start">{{ accompanyingGuests }}</p>
                        </div>
                        </form>
                    </div>
                    </div>
                </div>
                </div>

                <hr class="hr-block" />

                <div class="d-flex">
                <div class="row flex-grow-1 mx-2">
                    <div class="align-items">
                    <div class="d-flex flex-grow-1 justify-content-between pb-2 fw-bold">
                        Maximum Number of Loans
                    </div>
                    <div>
                        <form>
                        <div class="ms-2">
                            <p class="text-start">{{ maxLoans }}</p>
                        </div>
                        </form>
                    </div>
                    </div>
                </div>
                </div>

                <hr class="hr-block" />

                <div class="d-flex">
                <div class="row flex-grow-1 mx-2">
                    <div class="align-items">
                    <div class="d-flex flex-grow-1 justify-content-between pb-2 fw-bold">
                        Barcode Image
                    </div>
                    <div>
                        <form>
                            <img height="100" width="500" :src="barcodeImage === null ? '' : barcodeImage" alt="barcode image">
                        </form>
                    </div>
                    </div>
                </div>
                </div>
                <hr class="hr-block" />

            </div>
            </div>
        </div>
        </div>
    </div>
</template>

<script lang="ts">
import axios from "axios";
import { defineComponent } from "vue";

// Typings
interface ViewAttractionData {
    attractionId: number | null;
    attractionName: string | null;
    description: string | null;
    passType: string | null;
    cannotBook: boolean | null;
    barcodeImage: string | null;
    replacementFee: number | null;
    accompanyingGuests: number | null;
    maximumPasses: number | null;
    maxLoans: number | null;
    address: string | null;
    membershipID: string | null;
    expiryDate: string | null;
    benefits: string | null;
    termsConditions: string | null;
    staffId: number | null;
    role: string | null;
}

interface LoginData {
    staffId: number;
    role: string;
}

export default defineComponent({
    data(): ViewAttractionData {
        return {
            attractionName: null,
            description: null,
            address: null,
            membershipID: null,
            expiryDate: null,
            benefits: null,
            termsConditions: null,
            passType: null,
            cannotBook: null,
            barcodeImage: null,
            replacementFee: null,
            accompanyingGuests: null,
            maximumPasses: null,
            maxLoans: null,
            staffId: null,
            role: null,
            attractionId: this.$route.params.id instanceof Array 
                ? parseInt(this.$route.params.id[0]) 
                : parseInt(this.$route.params.id),
        };
    },
    async created() {
        const loginData = this.checkLogin();

        if (loginData !== undefined) {
            this.staffId = loginData.staffId;
            this.role = loginData.role;
        } else {
            this.$router.push({ name: "login" }).then(() => this.$router.go(0));
            return;
        }

        if (this.role !== "ADMINISTRATOR") {
            this.$router.push({ name: "home" }).then(() => this.$router.go(0));
            return;
        }

        try {
            const res = await axios.get(
                import.meta.env.VITE_API_URL + `api/attraction/${this.$route.params.id}`,
                {
                    headers: {'authorization': `${localStorage.getItem("token")}`},
                }
            );
            const data = await res.data;

            if (data.code === 200) {
                this.attractionName = data.data.name;
                this.description = data.data.description;
                this.address = data.data.address;
                this.membershipID = data.data.membershipId;
                this.expiryDate = data.data.expiryDate;
                this.benefits = data.data.benefits;
                this.termsConditions = data.data.termsConditions;
                this.passType = data.data.passType;
                this.cannotBook = data.data.cannotBook;
                this.barcodeImage = import.meta.env.VITE_API_URL + encodeURIComponent(data.data.barcodeImage.split('/')[0]) + '/' + encodeURIComponent(data.data.barcodeImage.split('/')[1]);
                this.replacementFee = data.data.replacementFee;
                this.accompanyingGuests = data.data.numAccompanyingGuests;
                this.maximumPasses = data.data.maxPassesPerLoan;
                this.maxLoans = data.data.maxLoansPerMonth;
            } else {
                this.$router.push({ name: "attraction" }).then(() => this.$router.go(0));
            }
        } catch (err: any) {
            if (err.response) {
                if (err.response.status == 401) {
                    this.$router.push({ name: "login" }).then(() => this.$router.go(0));
                } else if (err.response.status == 403) {
                    this.$router
                        .push({ name: "home" })
                        .then(() => this.$router.go(0));

                    return;
                } else {
                    console.error(err.response.data.message);
                }
            } else {
                console.error(err.message);
            }
        }
    },
    methods: {
        checkLogin(): LoginData | undefined {
            const staffIdStr = localStorage.getItem("staffId");
            const role = localStorage.getItem("role");

            if (staffIdStr === null || role === null) {
                this.$router.push({ name: "login" }).then(() => this.$router.go(0));
            } else {
                const staffId = parseInt(staffIdStr);

                return {
                staffId: staffId,
                role: role,
                };
            }
        },
    },
});
</script>

<style scoped>
.form-information {
    margin-top: 5px;
    margin-bottom: 30px;
    border: 2px solid black;
    border-radius: 10px;
    margin-left: 20%;
    margin-right: 20%;
    padding: 10px;
}
</style>
