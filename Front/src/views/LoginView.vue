<template>
  <v-container fluid fill-height class="bg">
    <v-row justify="start" class="t-bg mx-10">
      <v-sheet
        elevation="5"
        width="500px"
        min-height="500px"
        rounded="xl"
        color="grey darken-4"
        class="login-form"
      >
        <v-carousel
          hide-delimiters
          :show-arrows="false"
          v-model="carousel"
          height="100%"
        >
          <v-carousel-item>
            <login-info @regist="regist"></login-info>
          </v-carousel-item>
          <v-carousel-item>
            <basic-info
              @basicFinish="basicFinish"
              @cancel="cancel"
            ></basic-info>
          </v-carousel-item>
          <v-carousel-item>
            <league-info
              @leagueFinish="leagueFinish"
              @cancel="cancel"
            ></league-info>
          </v-carousel-item>
        </v-carousel>
      </v-sheet>
    </v-row>
  </v-container>
</template>

<script>
//axios를 쓰기 위한 변수 선언
const axios = require("axios");
const REST_API = `http://localhost:9999/api/user`;

import LoginInfo from "@/components/login/LoginInfo.vue";
import BasicInfo from "@/components/login/BasicInfo.vue";
import LeagueInfo from "@/components/login/LeagueInfo.vue";
export default {
  name: "LoginView",
  data() {
    return {
      carousel: 0,

      basics: {
        uId: "",
        pw: "",
        name: "",
      },
    };
  },
  components: {
    LoginInfo,
    BasicInfo,
    LeagueInfo,
  },

  methods: {
    // LoginInfo에서 회원가입을 누르면, 캐러셀이 옆(BasicInfo)으로 이동하여 출력
    regist() {
      this.carousel = 1;
    },
    // BasicInfo에서 다음 버튼을 누르면, 캐러셀이 옆(LeagueInfo)으로 이동하여 출력
    // payload는 emit을 통해 넘어온 객체
    basicFinish(payload) {
      this.basics.uId = payload.uId;
      this.basics.pw = payload.pw;
      this.basics.name = payload.name;
      this.carousel = 2;
    },

    // LeaguInfo에서 완료 버튼을 누르면, 캐러셀은 다시 처음(LoginInfo)로 이동하며,
    // axios 통신을 위해 가입정보(regiDat) 객체를 만들어 payload에 담아 "regist" action을 호출
    leagueFinish(payload) {
      const regData = {
        uId: this.basics.uId,
        pw: this.basics.pw,
        name: this.basics.name,
        nick: payload.nick,
        lId: payload.region + " " + payload.area,
        rImg: "", // String형과 맞게 하기 위함
        adCom: payload.adCom,
      };

      const API_URL = `${REST_API}/`;
      const formData = new FormData();
      formData.append("rImg", payload.rImg);
      axios({
        url: API_URL,
        method: "POST",
        params: regData,
        data: formData,
        headers: {
          "Content-Type": "multipart/form-data",
        },
      })
        .then(() => {
          alert(
            `${this.basics.name}님, 헬로 바디의 회원이 되신 것을 축하드립니다.`
          );
          this.carousel = 0;
        })
        .catch((err) => {
          alert("알 수 없는 오류로 가입에 실패하셨습니다.");
          console.log(err);
        });
    },
    // 각 캐러셀에서 이전으로 버튼을 누르면 이전 캐러셀로 넘어감
    cancel() {
      this.carousel = this.carousel - 1;
    },
  },
};
</script>

<style scoped>
.bg {
  background-image: url("@/assets/bg/loginbg.jpg");
  background-size: cover;
  background-position: center;
}

.login-form {
  color: RED;
}

.t-bg {
  background-color: transparent;
}
</style>
