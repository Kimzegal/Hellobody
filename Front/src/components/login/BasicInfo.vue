<template>
  <v-row justify="center">
    <v-col cols="8">
      <h1 class="mt-4 mb-8">회원가입</h1>
      <v-row class="abc">
        <v-text-field
          clearable
          outlined
          required
          background-color="grey"
          v-model.trim="signData.uId"
          placeholder="이메일 주소"
          color="indigo"
          :error-messages="emailErrors"
          @input="$v.signData.uId.$touch()"
          @blur="$v.signData.uId.$touch()"
        ></v-text-field>
      </v-row>
      <v-row class="crimson mt-n6 mb-n5" elevation="3" justify="end">
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
          v-model.trim="signData.pw"
          placeholder="비밀번호"
          type="password"
          color="indigo"
          :error-messages="passwordErrors"
          @input="$v.signData.pw.$touch()"
          @blur="$v.signData.pw.$touch()"
        ></v-text-field>
      </v-row>
      <v-row>
        <v-text-field
          clearable
          outlined
          required
          background-color="grey"
          v-model.trim="signData.pw2"
          placeholder="비밀번호 확인"
          type="password"
          color="indigo"
          :error-messages="sameErrors"
          @input="$v.signData.pw2.$touch()"
          @blur="$v.signData.pw2.$touch()"
        ></v-text-field>
      </v-row>
      <v-row>
        <v-text-field
          clearable
          outlined
          required
          background-color="grey"
          v-model.trim="signData.name"
          placeholder="이름"
          color="indigo"
          :error-messages="nameErrors"
          @input="$v.signData.name.$touch()"
          @blur="$v.signData.name.$touch()"
        ></v-text-field>
      </v-row>
      <v-row justify="center" class="mt-n3">
        <v-col cols="6">
          <v-btn color="grey" plain @click="cancel">이전으로</v-btn>
        </v-col>
        <v-col cols="6">
          <v-btn color="error" plain @click="verify">다음으로</v-btn>
        </v-col>
      </v-row>
    </v-col>
  </v-row>
</template>

<script>
//validators에서 유효성 검사용 객체를 가져옵니다.
import { required, sameAs, email, helpers } from "vuelidate/lib/validators";
const axios = require("axios");
const REST_API = `http://localhost:9999/api`;

export default {
  name: "BasicInfo",
  data() {
    return {
      signData: {
        uId: "",
        pw: "",
        pw2: "",
        nick: "",
      },
      check: false,
    };
  },
  //validations로 각 입력 form에 유효성을 검사합니다.
  validations: {
    signData: {
      uId: { required, email },
      name: { required, minLength: minLength(4), maxLength: maxLength(12) },
      pw: {
        required,
        pattern: helpers.regex(
          "pattern",
          /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[!@#$%^&*]).{8,}$/
        ),
      },
      pw2: {
        required,
        isSame: sameAs("pw"),
      },
    },
  },
  methods: {
    //getUserData는 아이디 중복확인용 데이터
    async getUserData() {
      const API_URL = `${REST_API}/user/${this.signData.uId}`;
      console.log(this.signData.uId + "에 대한 중복 여부를 검사합니다.");
      await axios({
        url: API_URL,
        method: "GET",
      })
        .then((res) => {
          this.check = res.data["confirm"];
          if (this.check) alert("사용 가능한 이메일 주소입니다.");
          else alert("이미 가입된 이메일 주소입니다.");
        })
        .catch((err) => {
          console.log(err);
        });
    },
    //verify는 전체 유효성을 확인한 후(invalid)에, 성공했을 때만 basicFinish이벤트를 emit함
    //basicInfo는 이메일 주소, 비밀번호, 이름을 담은 payload 객체
    verify() {
      this.$v.$touch();
      if (!this.$v.$invalid && this.check) {
        const basicInfo = {
          uId: this.signData.uId,
          pw: this.signData.pw,
          name: this.signData.name,
        };
        this.$emit("basicFinish", basicInfo);
      }
    },
    //cancel 메서드는 이전 페이지로 갈 수 있음
    cancel() {
      this.$emit("cancel");
    },
  },
  // computed를 사용하여, error 배열의 변화를 감지함
  // $dirty는 한번이라도 클릭했느냐 아니냐
  computed: {
    emailErrors() {
      const errors = [];
      if (!this.$v.signData.uId.$dirty) return errors;
      !this.$v.signData.uId.email &&
        errors.push("올바른 이메일 주소를 입력해야 합니다.");
      !this.$v.signData.uId.required &&
        errors.push("이메일 주소를 입력해주세요.");
      !this.check && errors.push("이메일 중복 여부를 확인하세요.");
      return errors;
    },
    passwordErrors() {
      const errors = [];
      if (!this.$v.signData.pw.$dirty) return errors;
      !this.$v.signData.pw.pattern &&
        errors.push(
          "비밀번호는 최소 8자 이상이어야 하며, 영문자와 숫자, 특수문자(!@#$%^&*)를 1개 이상 포함해야 합니다."
        );
      !this.$v.signData.pw.required && errors.push("비밀번호를 입력해주세요.");
      return errors;
    },
    sameErrors() {
      const errors = [];
      if (!this.$v.signData.pw2.$dirty) return errors;
      !this.$v.signData.pw2.required &&
        errors.push("비밀번호를 다시 확인해주세요.");
      !this.$v.signData.pw2.isSame && errors.push("비밀번호가 같지 않습니다.");
      return errors;
    },
    nameErrors() {
      const errors = [];
      if (!this.$v.signData.name.$dirty) return errors;
      !this.$v.signData.name.required && errors.push("이름을 입력해주세요.");
      return errors;
    },
  },
};
</script>

<style scoped>
h1 {
  color: white;
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
</style>
