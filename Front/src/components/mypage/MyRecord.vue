<template>
  <v-row>
    <v-col cols="12">
      <v-row justify="center">
        <v-card class="selectEx mr-n4 my-5" width="900" height="500">
          <v-img height="550" :src="`http://localhost:9999/img/${bg}`"> </v-img>
        </v-card>
        <v-bottom-navigation
          width="915"
          v-model="value"
          background-color="black"
          dark
          shift
        >
          <v-btn @click="showRecord(0)">
            <span>Bench</span>
            <v-icon>mdi-alpha-b-box</v-icon>
          </v-btn>
          <v-btn @click="showRecord(1)">
            <span>Dead</span>
            <v-icon>mdi-alpha-d-box</v-icon>
          </v-btn>
          <v-btn @click="showRecord(2)">
            <span>Squat</span>
            <v-icon>mdi-alpha-s-box</v-icon>
          </v-btn>
          <v-btn @click="showRecord(3)">
            <span>Over</span>
            <v-icon>mdi-alpha-o-box</v-icon>
          </v-btn>
          <v-btn @click="showRecord(4)">
            <span>curl</span>
            <v-icon>mdi-alpha-c-box</v-icon>
          </v-btn>
          <v-btn @click="showRecord(5)">
            <span>Leg</span>
            <v-icon>mdi-alpha-l-box</v-icon>
          </v-btn>
          <v-btn @click="showRecord(6)">
            <span>Pull</span>
            <v-icon>mdi-alpha-p-box</v-icon>
          </v-btn>
          <v-btn @click="showRecord(7)">
            <span>Push</span>
            <v-icon>mdi-alpha-p-box-outline</v-icon>
          </v-btn>
          <v-btn @click="showRecord(8)">
            <span>Plank</span>
            <v-icon>mdi-alpha-p</v-icon>
          </v-btn>
          <v-btn @click="showRecord(9)">
            <span>3km</span>
            <v-icon>mdi-numeric-3</v-icon>
          </v-btn>
          <v-btn @click="showRecord(10)">
            <span>10km</span>
            <v-icon>mdi-numeric-10</v-icon>
          </v-btn>
        </v-bottom-navigation>
      </v-row>
      <v-row>
        <v-card width="940" color="grey" tile>
          <v-toolbar color="grey-darken-4 text-right" dense dark>
            <v-toolbar-title>New Certification</v-toolbar-title></v-toolbar
          >
          <v-card-text>
            <v-row>
              <h3 v-show="clicked.hasOwnProperty('exName')">
                현재 최고 기록: {{ maxVal["certed"].exScore }} {{ cert.exUnit }}
              </h3>
            </v-row>
            <v-row>
              <v-col cols="8" class="mb-4">
                <v-file-input
                  label="영상"
                  filled
                  :disabled="notMe"
                  accept="video/mp4"
                  v-model="uploadVideo"
                  prepend-icon="mdi-camera"
                ></v-file-input>
                <v-divider />
                <p class="caption">
                  인증 대기 영상이 있을 경우 인증을 취소하고 새로 업로드만
                  가능합니다.
                </p>
              </v-col>
              <v-col cols="4">
                <v-text-field
                  label="기록"
                  type="number"
                  v-model="uploadScore"
                />
                <v-btn @click="upload" :disabled="notMe">제출</v-btn>
              </v-col>
            </v-row>
          </v-card-text>
        </v-card>
      </v-row>
    </v-col>
  </v-row>
</template>

<script>
import { mapState } from "vuex";

export default {
  data: () => ({
    bg: "loginbg.jpg",
    isClicked: false,
    clicked: {},
    uploadVideo: null,
    uploadScore: 0,
    exTitle: "",
    exDef: "",
    exStd: "",
    value: 0,
    items: [
      {
        src: "bench.png",
        title: "벤치 프레스",
        name: "벤치프레스",
      },
      {
        src: "dead.png",
        title: "데드 리프트",
        name: "데드리프트",
      },
      { src: "squat.png", title: "스쿼트", name: "스쿼트" },

      {
        src: "over.png",
        title: "오버 헤드 프레스",
        name: "오버헤드프레스",
      },
      {
        src: "curl.png",
        title: "바벨 컬",
        name: "바벨컬",
      },
      {
        src: "leg.png",
        title: "레그 프레스",
        name: "레그프레스",
      },
      {
        src: "pull.png",
        title: "풀 업",
        name: "풀업",
      },
      {
        src: "push.png",
        title: "푸시 업",
        name: "푸시업",
      },
      { src: "plank.png", title: "플랭크", name: "플랭크" },
      {
        src: "10km.png",
        title: "러닝(10km)",
        name: "10KM",
      },
      {
        src: "3km.png",
        title: "러닝(3km)",
        name: "3KM",
      },
    ],
  }),
  methods: {
    async showRecord(payload) {
      this.clicked["exName"] = this.items[payload].name;

      const requires = {
        uId: this.pageData.user.uId,
        exName: this.clicked.exName,
      };
      await this.$store.dispatch("getCert", requires);
      await this.$store.dispatch("getPageList", requires);
      this.$store.commit("MY_MAX_RECORD");
      console.log(this.clicked);
      this.isClicked = true;
      this.bg = this.items[payload].src;
    },

    upload() {
      const uploadPayload = {
        uId: this.user.uId,
        exName: this.clicked.exName,
        exScore: this.uploadScore,
        cVideo: this.uploadVideo,
      };

      if (this.maxVal["certed"].exScore >= uploadPayload.exScore) {
        alert("레코드 기록은 이전보다 높아야 합니다.");
      } else {
        this.$store.dispatch("upload", uploadPayload);
      }
    },
  },
  computed: {
    ...mapState(["user", "pageData", "cert", "maxVal"]),

    notMe() {
      return !(this.user.uId == this.pageData.user.uId);
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

.selectEx {
  background-color: black;
}
</style>
