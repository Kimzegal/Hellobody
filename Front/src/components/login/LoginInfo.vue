<template>
  <v-row justify="center">
    <v-col cols="8">
      <v-img width="100%" class="my-12" src="@/assets/hellobodymainlogo.png" />
      <v-text-field
        clearable
        outlined
        background-color="grey"
        v-model="uId"
        type="text"
        placeholder="이메일 주소"
        color="blue"
        @keyup.enter="login"
      ></v-text-field>
      <v-text-field
        clearable
        outlined
        background-color="grey"
        v-model="pw"
        type="password"
        placeholder="비밀번호"
        color="blue"
        @keyup.enter="login"
      />
      <v-row justify="center">
        <v-col cols="6">
          <v-btn color="grey" plain @click="login">로그인</v-btn>
        </v-col>
        <v-col cols="6">
          <v-btn color="error" plain @click="registPage">회원가입</v-btn>
        </v-col>
      </v-row>
    </v-col>
  </v-row>
</template>

<script>
import { mapState } from "vuex";
export default {
  name: "LoginInfo",
  data() {
    return {
      uId: "",
      pw: "",
    };
  },
  computed: {
    ...mapState(["user"]),
  },

  methods: {
    async login() {
      const loginInfo = {
        uId: this.uId,
        pw: this.pw,
      };
      // 유저 데이터를 가져오는 것을 기다려야 하므로, await 키워드를 작성
      await this.$store.dispatch("getUserData", loginInfo);
      if (localStorage.getItem("login-token") != null) {
        alert(this.user.nick + "님, 환영합니다.");
        this.$router.replace("/user/" + this.user.nick);
      } else {
        alert(
          "로그인에 실패하셨습니다 : 아이디 혹은 비밀번호가 잘못되었습니다."
        );
      }
    },

    //회원가입 버튼을 누를 경우, 회원가입 페이지로 넘어갈 수 있게 데이터를 넘긴다.
    registPage() {
      this.$emit("regist");
    },
  },
};
</script>
