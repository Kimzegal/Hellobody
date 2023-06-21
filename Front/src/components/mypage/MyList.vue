<template>
  <v-card color="black" tile>
    <v-toolbar color="grey-darken-4" dark dense>
      <v-toolbar-title>인증 현황</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-btn icon>
        <v-icon>mdi-magnify</v-icon>
      </v-btn>
    </v-toolbar>
    <v-list color="grey darken-4">
      <v-virtual-scroll
        :items="pageLists['list']"
        height="400"
        item-height="70"
      >
        <template v-slot:default="{ item }">
          <v-list-item-group v-model="selectedItem" color="primary" />
          <v-list-item :key="item.cId">
            <v-list-item-avatar v-if="!item['cDate']">
              <v-icon class="red darken-4" dark> mdi-alert-octagon </v-icon>
            </v-list-item-avatar>
            <v-list-item-avatar
              v-else-if="item['exScore'] >= cert['scores'][0]"
            >
              <v-icon class="cyan accent-3"> mdi-medal </v-icon>
            </v-list-item-avatar>
            <v-list-item-avatar
              v-else-if="item['exScore'] >= cert['scores'][1]"
            >
              <v-icon class="teal accent-3" dark> mdi-medal </v-icon>
            </v-list-item-avatar>
            <v-list-item-avatar
              v-else-if="item['exScore'] >= cert['scores'][2]"
            >
              <v-icon class="yellow darken-2" dark> mdi-medal </v-icon>
            </v-list-item-avatar>
            <v-list-item-avatar
              v-else-if="item['exScore'] >= cert['scores'][3]"
            >
              <v-icon class="grey lighten-2" dark> mdi-medal </v-icon>
            </v-list-item-avatar>
            <v-list-item-avatar v-else>
              <v-icon class="brown darken-3" dark> mdi-medal </v-icon>
            </v-list-item-avatar>
            <v-list-item-content class="mx-3">
              <v-list-item-title class="text-left text-h6" style="color: white">
                {{ item.exScore }} {{ cert.exUnit }}
              </v-list-item-title>
              <v-list-item-subtitle
                v-if="item['cDate'] != null"
                class="text-left"
                style="color: white"
              >
                인증일 : {{ item.cDate }}
              </v-list-item-subtitle>
              <v-list-item-subtitle v-else>
                업로드일 : {{ item.uDate }}
              </v-list-item-subtitle>
            </v-list-item-content>
            <v-list-item-content>
              <v-list-item-title class="caption mt-n3" style="color: white">
                Respect
              </v-list-item-title>
              <v-list-item-subtitle
                class="mt-2 font-weight-bold"
                style="color: white"
              >
                {{ item.respect }}
              </v-list-item-subtitle>
            </v-list-item-content>
            <v-list-item-icon class="mx-3">
              <v-icon color="yellow" large @click="goPage">
                mdi-file-eye
              </v-icon>
            </v-list-item-icon>
          </v-list-item>
          <v-divider></v-divider>
        </template>
      </v-virtual-scroll>
    </v-list>
  </v-card>
</template>

<script>
import { mapState } from "vuex";
export default {
  name: "MyList",
  data() {
    return {
      selectedItem: 1,
    };
  },
  methods: {
    goPage() {
      return;
    },
  },
  computed: {
    ...mapState(["pageData", "pageLists", "cert"]),
  },
};
</script>

<style></style>
