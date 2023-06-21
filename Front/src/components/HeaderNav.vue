<template>
  <v-row class="py-0">
    <v-app-bar prominent app color="black" flat>
      <v-row class="d-flex align-center justify-center">
        <v-col cols="2">
          <v-row justify="center">
            <v-btn text color="white" @click="myPage"> MY </v-btn>
          </v-row>
        </v-col>
        <v-col cols="8">
          <v-row>
            <v-img
              class="mt-2"
              contain
              width="300px"
              height="125px"
              src="../assets/hellobodymainlogo.png"
              @click="logout"
            ></v-img>
          </v-row>
        </v-col>
        <v-col cols="2">
          <v-btn text color="white" @click="crewPage"> crew </v-btn>
        </v-col>
      </v-row>
    </v-app-bar>
  </v-row>
</template>

<script>
export default {
  name: "HeaderNav",
  data: () => ({}),

  methods: {
    myPage() {
      this.$emit("goMyPage");
    },
    crewPage() {
      this.$emit("goCrewPage");
    },
    logout() {
      localStorage.removeItem("login-token");
      this.$store.commit("LOG_OUT");
      this.$router.replace("/login");
    },
  },
};
</script>

<style>
.py-0 {
  height: 125px;
}
</style>
