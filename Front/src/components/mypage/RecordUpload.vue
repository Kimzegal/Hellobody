<template>
  <v-app>
    <v-container>
      <v-row>
        <v-col
          v-for="n in 12"
          :key="n"
          class="d-flex child-flex ex-container"
          cols="2"
        >
          <v-img
            :src="require(`@/assets/exercise-icon/${items[n - 1].src}`)"
            aspect-ratio="1"
            class="white lighten-2 ex-icon"
            @click="showRecord(n - 1)"
          >
            <div class="ex-plain">{{ items[n - 1].title }}</div>
          </v-img>
        </v-col>
      </v-row>
      <v-divider />
      <div v-if="isClicked">
        <div>종목 : {{ clicked.exName }}</div>
        <div>최고 기록 : {{ clicked.exScore }}</div>
      </div>
    </v-container>
  </v-app>
</template>

<script>
export default {
  data: () => ({
    isClicked: false,
    clicked: {},
    items: [
      
    ],
  }),
  methods: {
    showRecord(payload) {
      const clickName = this.items[payload].name;
      for (let record of this.maxRecords) {
        if (record.exName == clickName) {
          this.clicked = record;
        }
      }
      console.log(this.clicked);
      this.isClicked = true;
    },
  },
  computed: {
    maxRecords() {
      return this.$store.state.maxExerValue;
    },
  },
};
</script>

<style>
.ex-container:hover .ex-plain {
  opacity: 1;
}
.ex-icon {
  padding: 10px;
  border: 10px solid;
  filter: brightness(1);
}
.ex-icon:hover {
  filter: brightness(0.5);
}
.ex-plain {
  position: absolute;
  opacity: 0;
  text-align: center;
}
</style>
