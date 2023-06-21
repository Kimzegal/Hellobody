<template>
  <div class="home">
    <header-nav @goMyPage="goMyPage" @goLeaguePage="goLeaguePage" />
    <v-row>
      <v-col cols="12">
        <div class="table-container">
          <h3 class="table-title">EXERCISE RECORDS</h3>
          <v-simple-table class="centered-table">
            <thead>
              <th class="text-center">EXERCISE</th>
              <th class="text-center">ID</th>
              <th class="text-center">Score</th>
              <th class="text-center">RESPECT</th>
            </thead>
            <tbody>
              <tr v-for="item in records" :key="item.cId">
                <td @click="openCertificationVideo(item)">{{ item.exName }}</td>
                <td>{{ item.uId }}</td>
                <td>{{ item.exScore }}</td>
                <td>{{ item.respect }}</td>
              </tr>
            </tbody>
          </v-simple-table>
        </div>
      </v-col>
    </v-row>
    <v-dialog v-model="dialogVisible" max-width="1300" max-height="1300">
      <v-card>
        <!-- 팝업 내용을 추가하세요 -->
        <v-card-title>인증 영상</v-card-title>
        <v-card-text>
          <video :src="`${currentVideoUrl}`" type="video/mp4" controls></video>

          <div>
            <v-simple-table class="comment-table">
              <template v-slot:default>
                <thead>
                  <tr>
                    <th class="text-center comment-space"></th>
                    <th class="text-center comment-id">ID</th>
                    <th class="text-center comment-content">댓글</th>
                    <th class="text-center comment-date">작성일</th>
                    <th class="text-center comment-space"></th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="comment in comments" :key="comment.rId">
                    <td class="comment-space"></td>
                    <td class="comment-id">{{ comment.uId }}</td>
                    <td class="comment-content">{{ comment.rContent }}</td>
                    <td class="comment-rDate">{{ comment.rDate }}</td>
                    <td class="comment-space">
                      <v-icon
                        small
                        v-if="comment.uId === user.uId"
                        @click="deleteComment(comment)"
                        >mdi-delete</v-icon
                      >
                    </td>
                  </tr>
                </tbody>
              </template>
            </v-simple-table>
          </div>
        </v-card-text>
        <v-card-actions>
          <v-btn color="primary" text @click="closeCertificationVideo"
            >CLOSE</v-btn
          >
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
import { mapState, mapActions } from "vuex";
import HeaderNav from "@/components/HeaderNav.vue";
export default {
  data() {
    return {
      dialogVisible: false, // 팝업 창의 표시 여부를 제어하는 데이터 속성

      currentVideoUrl: "",
    };
  },
  name: "CertView",
  beforeCreate() {
    if (!localStorage.getItem("login-token")) {
      alert("로그인 해주세요.");
      this.$router.replace("/login");
    } else {
      this.$store.commit("LOGIN_USER");
      this.$store.dispatch("getAllRecords");
    }
  },
  components: {
    HeaderNav,
  },

  computed: {
    ...mapState(["user"]),
    records() {
      return this.$store.state.allrecords;
    },
    comments() {
      return this.$store.state.comments;
    },
  },
  methods: {
    ...mapActions(["fetchComments", "deleteComment"]),

    goMyPage() {
      console.log("ㅎㅇ");
      if (this.$route.path != `/user/${this.user.nick}`)
        this.$router.replace(`/user/${this.user.nick}`);
    },
    async openCertificationVideo(item) {
      this.currentVideoUrl = `http://localhost:9999/video/${item.cVideo}`; //`http://localhost:9999/video/${cVideo}`
      console.log("URL");
      console.log(this.currentVideoUrl);
      this.dialogVisible = true;

      await this.fetchComments(item.cId);
      console.log("Fetching comments for cId:", item.cId);
    },
    deleteComment(comment) {
      this.$store.dispatch("deleteComment", comment);
    },

    goLeaguePage() {
      if (this.$route.path != "/league") this.$router.replace("/league");
    },
    closeCertificationVideo() {
      this.dialogVisible = false;
      this.currentVideoUrl = "";
    },
  },
};
</script>

<style>
.board-item {
  padding: 5px;
}
.centered-table {
  margin: 0 auto; /* 테이블을 수평으로 가운데로 정렬합니다. */
}
.table-container {
  padding: 200px; /* 테이블 주위에 공백을 추가합니다. */
}

.table-title {
  text-align: center; /* 제목을 가운데 정렬합니다. */
  padding-top: 120px;
  margin-bottom: 10px; /* 제목 아래에 공백을 추가합
  니다. */
}
.comment-table {
  width: 100%; /* 테이블의 전체 너비를 100%로 설정합니다. */
}

.comment-space {
  width: 10%; /* 양쪽 공백 열의 너비를 10%로 설정합니다. */
}

.comment-id {
  width: 15%; /* ID 열의 너비를 20%로 설정합니다. */
  font-size: 12px; /* ID 열의 글꼴 크기를 작게 조정합니다. */
}

.comment-content {
  width: 50%; /* 댓글 내용 열의 너비를 60%로 설정합니다. */
  white-space: normal; /* 댓글 내용이 길어질 경우 자동으로 줄바꿈합니다. */
}
.comment-date {
  width: 15%; /* 댓글 내용 열의 너비를 60%로 설정합니다. */
  white-space: normal; /* 댓글 내용이 길어질 경우 자동으로 줄바꿈합니다. */
}
</style>
