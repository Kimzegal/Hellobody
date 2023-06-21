<template>
  <v-app id="app">
    <v-app-bar color="black" dense dark>
      <v-toolbar-title class="red--text" v-if="this.$route.path != '/login'"
        >Welcome to HELL O BODY</v-toolbar-title
      >

      <v-spacer></v-spacer>

      <v-btn
        plain
        dark
        v-if="!this.$route.path.includes('user')"
        @click="goMyPage"
      >
        mypage
      </v-btn>
      <v-btn plain color="red" dark v-else @click="goMyPage"> mypage </v-btn>
      <v-spacer></v-spacer>

      <v-btn
        plain
        dark
        v-if="this.$route.path != '/league'"
        @click="goLeaguePage"
      >
        league
      </v-btn>
      <v-btn plain color="red" dark v-else @click="goLeaguePage">
        league
      </v-btn>

      <v-spacer></v-spacer>

      <v-btn plain dark v-if="this.$route.path != '/cert'" @click="goCertPage">
        record
      </v-btn>
      <v-btn plain color="red" dark v-else @click="goCertPage"> record </v-btn>

      <v-spacer></v-spacer>
      <v-spacer></v-spacer>
      <v-btn plain color="red" dark @click="logout"> logout </v-btn>
    </v-app-bar>
    <router-view />
  </v-app>
</template>

<script>
import { mapState } from "vuex";
export default {
  name: "WelcomePage",
  data() {
    return {};
  },
  components: {},

  methods: {
    goLeaguePage() {
      if (this.$route.path != "/league") this.$router.replace("/league");
    },
    goCertPage() {
      if (this.$route.path != "/cert") this.$router.replace("/cert");
    },
    goMyPage() {
      if (this.$route.path != `/user/${this.user.nick}`)
        this.$router.replace(`/user/${this.user.nick}`);
    },
    logout() {
      localStorage.removeItem("login-token");
      this.$store.commit("LOG_OUT");
      this.$router.replace("/login");
    },
  },

  computed: {
    ...mapState(["user"]),
  },
};
</script>

<style>
@import url("https://webfontworld.github.io/naver/NanumBarunGothic.css");

#app {
  font-family: "NanumBarunGothic";
  text-align: center;
  color: #2c3e50;
}
</style>
