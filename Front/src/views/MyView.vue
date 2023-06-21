<template>
  <v-container fluid fill-height class="bg mb-n5">
    <v-row justify="center">
      <v-row justify="center">
        <v-card color="black" tile width="1000" height="100"> </v-card>
      </v-row>
      <v-card color="black" tile width="70%" height="auto">
        <v-system-bar window color="grey darken-4" dark>
          <v-icon>mdi-message</v-icon>
          <span>League Ranking</span>
          <v-spacer></v-spacer>
          <v-icon>mdi-checkbox-blank-outline</v-icon>
        </v-system-bar>
        <v-row justify="center" class="rank-table">
          <v-col cols="12">
            <v-row>
              <v-col cols="4">
                <v-row justify="center">
                  <v-card class="mt-3 ml-6" tile color="black">
                    <my-card />
                  </v-card>
                </v-row>
                <v-row justify="center">
                  <v-card width="100%" class="mt-3 ml-3" tile color="black">
                    <my-list />
                  </v-card>
                </v-row>
              </v-col>
              <v-col cols="8">
                <v-row justify="center">
                  <v-card width="100%" class="mt-3 ml-6" tile color="black">
                    <my-record />
                  </v-card>
                </v-row>
              </v-col>
            </v-row>
          </v-col>
        </v-row>
        <v-system-bar window color="grey darken-4" dark> </v-system-bar>
      </v-card>
    </v-row>
  </v-container>
</template>

<script>
import { mapState } from "vuex";
// const axios = require("axios");
// const REST_API = `http://localhost:9999/`;
import MyCard from "../components/mypage/MyCard.vue";
import MyRecord from "../components/mypage/MyRecord.vue";
import MyList from "../components/mypage/MyList.vue";

export default {
  name: "MyView",
  async beforeCreate() {
    const payload = {
      nick: this.$route.params.nick,
    };
    await this.$store.dispatch("getPageInfo", payload);
  },

  beforeMount() {},
  data: () => ({}),
  computed: {
    ...mapState(["user", "pageData"]),
  },
  methods: {},
  components: {
    MyCard,
    MyRecord,
    MyList,
  },
};
</script>

<style scoped>
.bg {
  background-color: black;
  background-size: cover;
  background-position: center;
}
</style>
