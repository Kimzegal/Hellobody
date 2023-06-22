<!-- 회원가입 / 로그인과 관련된 view를 정의합니다. -->

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
/* 
LoginView에서만 사용하는 Ajax 통신을 위한 변수를 선언합니다.
*/
const axios = require("axios");
const REST_API = `http://localhost:9999/api/user`;

/* 하위 컴포넌트가 Vuetify 캐러셀에 적용됩니다.
  LoginInfo : 로그인과 관련된 컴포넌트
  BasicInfo : 기본적인 정보를 입력하는 컴포넌트
  LeagueInfo : 리그(지역)과 관련된 정보를 입력하는 컴포넌트
*/
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
    // LoginInfo에서 회원가입을 누르면, 캐러셀이 옆(BasicInfo)으로 이동하는 메소드입니다.
    regist() {
      this.carousel = 1;
    },

    // BasicInfo에서 다음 버튼을 누르면, 캐러셀이 옆(LeagueInfo)으로  이동하는 메소드입니다.
    basicFinish(payload) {
      this.basics["uId"] = payload["uId"];
      this.basics["pw"] = payload["pw"];
      this.basics["name"] = payload["name"];
      this.carousel = 2;
    },

    // LeaguInfo에서 완료 버튼을 누르면, 캐러셀은 다시 처음(LoginInfo)로 이동하며,
    // axios 통신을 위해 가입정보(regiDat) 객체를 만들어 payload에 담아 "regist" action을 호출합니다.
    leagueFinish(payload) {
      const regData = {
        uId: this.basics["uId"],
        pw: this.basics["pw"],
        name: this.basics["name"],
        nick: payload["nick"],
        lId: payload["region"] + " " + payload["area"],
        rImg: "", // 백의 String형과 맞게 하기 위함
        adCom: payload["adCom"],
      };

      const API_URL = `${REST_API}/`;
      //FormData 객체에, 이미지와 관련된 멀티파트 데이터를 담습니다.
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
            `${this.basics["name"]}님, Hellobody의 회원이 되신 것을 축하드립니다.`
          );
          this.carousel = 0;
        })
        .catch((err) => {
          alert("가입에 실패하셨습니다. 다시 시도해주세요.");
          console.log(err);
        });
    },

    // 각 캐러셀에서 이전으로 버튼을 누르면 이전 캐러셀로 넘어갑니다.
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
