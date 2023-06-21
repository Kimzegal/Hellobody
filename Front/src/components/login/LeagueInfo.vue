<template>
  <v-row justify="center">
    <v-dialog v-model="dialog" width="500">
      <v-card>
        <v-card-title class="text-h5 grey lighten-2"> 리그 설명 </v-card-title>
      </v-card>
    </v-dialog>
    <v-col cols="8" class="my-4 abc">
      <v-row justify="center">
        <v-col cols="2"></v-col>
        <v-col cols="8"><h1 class="my-3">리그 정보 입력</h1></v-col>
        <v-col cols="2" class="d-flex align-end mb-3">
          <div class="caption white--text" @click="popLeague">
            리그란?
          </div></v-col
        >
      </v-row>
      <v-row class="crimson my-n5" elevation="3" justify="start">
        <p
          class="caption font-weight-bold text-decoration-underline"
          @click="getUserData"
        >
          중복확인
        </p>
      </v-row>
      <v-row class="abc">
        <v-text-field
          clearable
          outlined
          required
          background-color="grey"
          v-model.trim="signData.nick"
          placeholder="닉네임"
          color="indigo"
          :counter="20"
          :error-messages="nickErrors"
          @input="$v.signData.nick.$touch()"
          @blur="$v.signData.nick.$touch()"
        ></v-text-field>
      </v-row>
      <v-row class="abc">
        <v-textarea
          clearable
          outlined
          background-color="grey"
          v-model="signData.adCom"
          placeholder="코멘트"
          color="indigo"
          :counter="50"
          :error-messages="adComErrors"
          @input="$v.signData.adCom.$touch()"
          @blur="$v.signData.adCom.$touch()"
        ></v-textarea>
      </v-row>
      <v-row class="file-input">
        <v-file-input
          label="대표 이미지"
          show-size
          accept="image/png, image/jpeg, image/gif"
          v-model="signData.rImg"
          :rules="rules"
        ></v-file-input>
      </v-row>
      <v-row class="crimson" justify="end">
        <p class="caption">미설정시 기본 이미지로 설정 (이후 변경 가능)</p>
      </v-row>
      <v-row class="mt-3" justify="space-between">
        <v-col cols="5">
          <v-row justify="start">
            <v-select
              :items="regions"
              v-model="signData.region"
              label="지역"
              background-color="grey"
              solo
              :error-messages="regionErrors"
              @input="$v.signData.region.$touch()"
              @blur="$v.signData.region.$touch()"
            ></v-select>
          </v-row>
        </v-col>
        <v-col cols="5">
          <v-row justify="end">
            <v-select
              :items="areas"
              v-model="signData.area"
              label="상세지역"
              background-color="grey"
              solo
              :error-messages="areaErrors"
              @input="$v.signData.area.$touch()"
              @blur="$v.signData.area.$touch()"
            ></v-select>
          </v-row>
        </v-col>
      </v-row>
      <v-row class="mt-n5" justify="center">
        <v-col cols="6">
          <v-btn color="grey" plain @click="cancel">이전으로</v-btn>
        </v-col>
        <v-col cols="6">
          <v-btn color="error" plain @click="regist">완료</v-btn>
        </v-col>
      </v-row>
    </v-col>
  </v-row>
</template>

<script>
//validators에서 유효성 검사용 객체를 가져옴
import { required, maxLength } from "vuelidate/lib/validators";
import { mapState } from "vuex";
const axios = require("axios");
const REST_API = `http://localhost:9999/api`;
export default {
  name: "LeagueInfo",

  data() {
    return {
      signData: {
        nick: "",
        region: "서울특별시",
        area: "",
        rImg: null,
        adCom: "",
      },

      rules: [
        (value) =>
          !value ||
          value.size < 2000000 ||
          "Avatar size should be less than 2 MB!",
      ],

      check: false,
      dialog: false,
    };
  },

  validations: {
    signData: {
      nick: { required, maxLength: maxLength(15) },
      adCom: { required, maxLength: maxLength(50) },
      region: { required },
      area: { required },
    },
  },

  // 컴포넌트가 created되면 leagueData를 읽어오도록 한다.
  beforeCreate() {
    this.$store.dispatch("getLeagueData");
  },

  computed: {
    // vuex의 leagueData에서 region 목록과 그에 따른 area 목록을 꺼내온다.
    ...mapState(["leagueData"]),
    // leagueData의 모든 키값을 배열로 반환(대분류)
    regions() {
      return Object.keys(this.leagueData);
    },
    // 선택한 대분류에 해당하는 소분류 배열을 반환
    areas() {
      return this.leagueData[this.signData.region];
    },

    // BasicInfo와 동일하게 유효성을 검사한다.
    nickErrors() {
      const errors = [];
      if (!this.$v.signData.nick.$dirty) return errors;
      !this.$v.signData.nick.maxLength &&
        errors.push("닉네임은 최대 15글자까지 가능합니다.");
      !this.$v.signData.nick.required && errors.push("닉네임을 입력해주세요.");
      !this.check && errors.push("닉네임 중복 여부를 확인하세요.");
      return errors;
    },
    adComErrors() {
      const errors = [];
      if (!this.$v.signData.adCom.$dirty) return errors;
      !this.$v.signData.adCom.maxLength &&
        errors.push("코멘트는 최대 50글자까지 가능합니다.");
      return errors;
    },
    regionErrors() {
      const errors = [];
      if (!this.$v.signData.region.$dirty) return errors;
      !this.$v.signData.region.required && errors.push("지역을 선택해주세요.");
      return errors;
    },
    areaErrors() {
      const errors = [];
      if (!this.$v.signData.area.$dirty) return errors;
      !this.$v.signData.area.required &&
        errors.push("상세 지역을 선택해주세요.");
      return errors;
    },
  },

  methods: {
    //getUserData는 아이디 중복확인용 데이터
    async getUserData() {
      const API_URL = `${REST_API}/user/nick/${this.signData.nick}`;
      console.log(this.signData.nick + "에 대한 중복 여부를 검사합니다.");
      await axios({
        url: API_URL,
        method: "GET",
      })
        .then((res) => {
          this.check = res.data["confirm"];
          if (this.check) alert("사용 가능한 닉네임입니다.");
          else alert("이미 존재하는 닉네임입니다.");
        })
        .catch((err) => {
          console.log(err);
        });
    },

    // 회원가입이 완료되면 valid 목록을 확인해본 뒤, 유효성이 괜찮다면 emit
    regist() {
      this.$v.$touch();
      if (!this.$v.$invalid && this.check) {
        const leagueInfo = {
          nick: this.signData.nick,
          rImg: this.signData.rImg,
          region: this.signData.region,
          area: this.signData.area,
          adCom: this.signData.adCom,
        };
        this.$emit("leagueFinish", leagueInfo);
      }
    },
    //cancel 메서드는 이전 페이지로 갈 수 있음
    cancel() {
      this.$emit("cancel");
    },

    popLeague() {
      this.dialog = true;
    },
  },
};
</script>

<style scoped>
h1 {
  color: white;
}
.file-input {
  background-color: #9e9e9e;
}
.abc {
  z-index: 1;
  position: relative;
}
.crimson {
  color: #a33e3e;
  z-index: 2;
  position: relative;
}
.caption {
  white-space: nowrap;
}
</style>
