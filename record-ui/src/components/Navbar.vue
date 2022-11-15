<template>
  <div class="header">
    <RouterLink to="/">
      <img
        alt="Vue logo"
        class="logo header-logo"
        src="@/assets/logo.png"
        width="160"
        height="75" role="button"
      />
    </RouterLink>

    <div class="header-nav">
      <nav v-if="role === 'BORROWER'" class="topnav-right">
        <a
          v-for="route, idx in borrowerRoutes"
          :key="idx"
          @click="changeRoutes(route.path, route.name)"
          class="place lato mx-auto route-link"
        >
          <div
            v-if="route.name === 'Log Out'"
            class="auto-layout-horizontal display"
          >
            {{ route.name }}
          </div>
          <div 
            v-else
            class="lato2 display"
          >
            {{ route.name }}
          </div>
        </a>
      </nav>
      <nav v-if="role === 'ADMINISTRATOR'" class="topnav-right">
        <a
          v-for="route, idx in adminRoutes"
          :key="idx"
          @click="changeRoutes(route.path, route.name)"
          class="place lato mx-auto route-link"
        >
          <div
            v-if="route.name === 'Log Out'"
            class="auto-layout-horizontal display"
          >
            {{ route.name }}
          </div>
          <div 
            v-else
            class="lato2 display"
          >
            {{ route.name }}
          </div>
        </a>
      </nav>
      <nav v-if="role === 'GOP'" class="topnav-right">
        <a
          v-for="route, idx in gopRoutes"
          :key="idx"
          @click="changeRoutes(route.path, route.name)"
          class="place lato mx-auto route-link"
        >
          <div
            v-if="route.name === 'Log Out'"
            class="auto-layout-horizontal display"
          >
            {{ route.name }}
          </div>
          <div 
            v-else
            class="lato2 display"
          >
            {{ route.name }}
          </div>
        </a>
      </nav>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import {RouterLink} from 'vue-router'

// Typings
interface Route{
  path: string,
  name: string,
}

interface LoginData {
  staffId: Number,
  role: String,
}

interface NavbarData {
  adminRoutes: Route[],
  gopRoutes: Route[],
  borrowerRoutes: Route[],
  role: String | null,
  staffId: Number | null,
}

export default defineComponent({
  name: 'NavBar',
  data(): NavbarData {
    const adminRoutes: Route[] = [
      {
        path: "/admin/attractions",
        name: "Attractions",
      },
      {
        path: "/admin/passes",
        name: "Passes",
      },
      {
        path: "/admin/bookings",
        name: "Loans",
      },
      {
        path: '/admin/staffs',
        name: 'Staffs',
      },
      {
        path: "/bookings",
        name: "My Bookings"
      },
      {
        path: "/profile",
        name: 'My Profile',
      },
      {
        path: "/",
        name: "Log Out",
      }
    ];

    const gopRoutes: Route[] = [
      {
        path: "/GOP/bookings",
        name: "Loans"
      },
      {
        path: "/bookings",
        name: "My Bookings"
      },
      {
        path: "/profile",
        name: 'My Profile',
      },
      {
        path: "/",
        name: "Log Out",
      }
    ];

    const borrowerRoutes: Route[] = [
      {
        path: "/bookings",
        name: "My Bookings"
      },
      {
        path: "/profile",
        name: 'My Profile',
      },
      {
        path: "/",
        name: "Log Out",
      }
    ];

    return {
      staffId: null,
      role: null,
      adminRoutes: adminRoutes,
      gopRoutes: gopRoutes,
      borrowerRoutes: borrowerRoutes,
    };
  },
  components: {
    RouterLink,
  },
  created() {
    const loginData: LoginData | undefined = this.checkLogin();

    if (loginData !== undefined) {
      this.staffId = loginData.staffId;
      this.role = loginData.role;
    }
  },
  methods: {
    checkLogin(): LoginData | undefined {
        let staffIdStr = localStorage.getItem("staffId");
        let role = localStorage.getItem("role");

        if (staffIdStr === null || role === null) {
            this.$router.push({ name: 'login' });
        } else {
            let staffId = parseInt(staffIdStr);

            return {
                staffId: staffId,
                role: role,
            };
        }
    },
    changeRoutes(path: string, name: string) {
      if (name === "Log Out") {
        localStorage.removeItem("staffId");
        localStorage.removeItem("role");

        this.$router.push({ name: 'login' }).then(() => this.$router.go(0));
      } else {
        this.$router.push(path).then(() => this.$router.go(0));
      }
    },
  }
});
</script>

<style>
.header {
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;
}
.header{
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;
}
.header-nav {
  flex-grow: 1;
  padding-left: 3%;
  padding-right: 3%;
}

.header-nav-item {
  padding-left: 3%;
  padding-right: 3%;
}

.main-content {
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.place {
  letter-spacing: 0;
  line-height: 20px;
  margin-left: 500px;
  margin-top: 2px;
  min-height: 20px;
  min-width: 73px;
  white-space: nowrap;
}

.lato {
  color: #fff;
  font-family: "Lato", sans-serif;
  font-size: 17px;
  font-style: normal;
  text-decoration: none;
}

.lato2 {
  color: #f57921;
  font-family: "Lato", sans-serif;
  font-size: 17px;
  font-weight: bolder;
  font-style: normal;
  text-decoration: none;
  border: 1px none;
  border-radius: 16px;
  display: flex;
  gap: 10px;
  height: 55px;
  justify-content: center;
  margin-left: 20px;
  margin-top: 1px;
  padding: 20px 32px;
  width: 129px;
}

.sign-in {
  letter-spacing: 0;
  line-height: 24px;
  margin-bottom: -7.5px;
  margin-top: -9.5px;
  white-space: nowrap;
  width: fit-content;
  color: rgb(255, 255, 255);
  font-family: Arial, Helvetica, sans-serif;
  font-size: 20px;
}

.auto-layout-horizontal {
  align-items: center;
  font-weight: bolder;
  background-color: #d91c53;
  border: 1px none;
  border-radius: 16px;
  display: flex;
  gap: 10px;
  height: 55px;
  justify-content: center;
  margin-left: 45px;
  margin-top: 1px;
  padding: 20px 32px;
  width: 129px;
}

.topnav-right {
  float: right;
}

.display {
  display: inline-block;
}

.route-link:hover {
  cursor: pointer;
  color: white;
}
</style>
