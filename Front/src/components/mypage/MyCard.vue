<template>
  <v-card color="black" class="mx-auto my-3">
    <v-toolbar color="grey-darken-4" dark dense>
      <v-toolbar-title></v-toolbar-title>
      <v-spacer></v-spacer>
      <v-btn icon>
        <v-icon>mdi-magnify</v-icon>
      </v-btn>
    </v-toolbar>
    <v-row>
      <v-col cols="5">
        <v-img
          class="my-2 profile"
          height="250"
          :src="`http://localhost:9999/img/${pageData.user['rImg']}`"
        ></v-img>
      </v-col>
      <v-col cols="7">
        <v-row>
          <v-sheet height="50"> </v-sheet>
        </v-row>
        <v-row class="d-flex align-center">
          <v-col cols="3" style="padding: 0px">
            <v-img
              contain
              class="my-2"
              max-height="100"
              src="../img/gold.png"
            ></v-img>
          </v-col>
          <v-col cols="7" style="padding: 0px">
            <v-row justify="end">
              <h1 class="mt-1 white--text">{{ pageData.user["nick"] }}</h1>
            </v-row>
            <v-row justify="end">
              <h6 class="white--text">{{ pageData.user["lId"] }}</h6>
            </v-row>
          </v-col>
        </v-row>
        <v-row>
          <v-divider />
        </v-row>
        <v-row>
          <v-card-text class="white--text">
            {{ pageData.user["adCom"] }}
          </v-card-text>
        </v-row>
      </v-col>
    </v-row>
  </v-card>
</template>

<script>
import { mapState } from "vuex";
export default {
  name: "MyCard",
  data() {
    return {};
  },
  computed: {
    ...mapState(["user", "pageData"]),
  },
  methods: {},
  components: {},
};
</script>

<style>
.column {
  padding: 0px;
}
</style>
