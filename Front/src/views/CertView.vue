<template>
  <v-container fluid fill-height class="bg my-n5">
    <v-row justify="center">
      <v-row justify="center" align="center">
        <v-sheet color="black" tile width="1000" height="100"> </v-sheet>
      </v-row>
      <v-card color="black" tile width="70%" height="auto">
        <v-system-bar window color="grey darken-4" dark class="bar">
          <v-icon>mdi-message</v-icon>
          <span>Records & Comments</span>
          <v-spacer></v-spacer>
          <v-icon>mdi-checkbox-blank-outline</v-icon>
        </v-system-bar>
        <v-row justify="center">
          <v-col cols="12">
            <v-row>
              <v-col cols="5">
                <v-card color="black" tile height="800">
                  <template>
                    <div>
                      <v-simple-table dark fixed-header height="750">
                        <template v-slot:default>
                          <thead>
                            <tr>
                              <th class="text-center">EXERCISE</th>
                              <th class="text-center">ID</th>
                              <th class="text-center">Score</th>
                              <th class="text-center">RESPECT</th>
                            </tr>
                          </thead>
                          <tbody>
                            <tr v-for="item in paginatedData" :key="item.cId">
                              <td @click="openCertificationVideo(item)">
                                {{ item.exName }}
                              </td>
                              <td>{{ item.uId }}</td>
                              <td>{{ item.exScore }}</td>
                              <td>{{ item.respect }}</td>
                            </tr>
                          </tbody>
                        </template>
                      </v-simple-table>
                      <v-pagination
                        v-model="currentPage"
                        :length="pageCount"
                        dark
                      ></v-pagination>
                    </div>
                  </template>
                </v-card>
              </v-col>
              <v-col cols="7">
                <v-row class="mt-2 mb-n5">
                  <v-card
                    tile
                    width="100%"
                    height="804"
                    dark
                    class="ml-n3 mt-n3"
                  >
                    <v-system-bar window color="black" dark class="bar">
                      인증 영상</v-system-bar
                    >
                    <v-row justify="center">
                      <video
                        width="80%"
                        height="auto"
                        :src="`${currentVideoUrl}`"
                        type="video/mp4"
                        controls
                        class="my-3"
                      ></video>
                    </v-row>
                    <v-row justify="center">
                      <v-col cols="12">
                        <v-simple-table
                          class="comment-table"
                          fixed-header
                          height="300"
                        >
                          <template v-slot:default>
                            <thead>
                              <tr>
                                <th class="text-center comment-space"></th>
                                <th class="text-center comment-id">ID</th>
                                <th class="text-center comment-content">
                                  댓글
                                </th>
                                <th class="text-center comment-date">작성일</th>
                                <th class="text-center comment-space"></th>
                              </tr>
                            </thead>
                            <tbody>
                              <tr
                                v-for="comment in comments"
                                :key="comment.rId"
                              >
                                <td class="comment-space"></td>
                                <td class="comment-id">{{ comment.uId }}</td>
                                <td class="comment-content">
                                  {{ comment.rContent }}
                                </td>
                                <td class="comment-rDate">
                                  {{ comment.rDate }}
                                </td>
                                <td class="comment-space">
                                  <v-icon
                                    small
                                    v-if="
                                      comment.uId == user.uId || user.aId == 1
                                    "
                                    @click="deleteComment(comment)"
                                    >mdi-delete</v-icon
                                  >
                                  <v-icon
                                    small
                                    v-if="comment.uId == user.uId"
                                    @click="dialog2(comment)"
                                    >mdi-update</v-icon
                                  >
                                </td>
                              </tr>
                            </tbody>
                          </template>
                        </v-simple-table>
                      </v-col>
                    </v-row>
                    <v-row justify="space-around">
                      <v-btn depressed color="green" @click="dialog"
                        >작성</v-btn
                      >
                      <v-btn depressed color="blue" @click="addRespsect"
                        >리스펙</v-btn
                      >
                      <v-btn
                        depressed
                        color="red"
                        class="mx-n3"
                        v-if="user.mId == 1 && this.pof == 0"
                        @click="certif"
                        >인증</v-btn
                      >
                    </v-row>
                    <v-dialog v-model="dialogVisible" width="500">
                      <v-card dark>
                        <v-card-title class="text-h5 grey darken-4">
                          댓글 작성
                        </v-card-title>
                        <v-row justify="center">
                          <v-col cols="12">
                            <v-row class="abc">
                              <v-textarea
                                class="my-3 mx-3"
                                clearable
                                outlined
                                background-color="grey"
                                v-model="mycomment"
                                placeholder="코멘트"
                                color="indigo"
                                :counter="100"
                              ></v-textarea>
                            </v-row>
                            <v-row justify="center" class="mt-n5">
                              <v-col cols="6">
                                <v-btn color="grey" plain @click="dialog"
                                  >취소</v-btn
                                >
                              </v-col>
                              <v-col cols="6">
                                <v-btn color="error" plain @click="addComment"
                                  >등록</v-btn
                                >
                              </v-col>
                            </v-row>
                          </v-col>
                        </v-row>
                      </v-card>
                    </v-dialog>
                    <v-dialog v-model="dialogVisible2" width="500">
                      <v-card dark>
                        <v-card-title class="text-h5 grey darken-4">
                          댓글 수정
                        </v-card-title>
                        <v-row justify="center">
                          <v-col cols="12">
                            <v-row class="abc">
                              <v-textarea
                                class="my-3 mx-3"
                                clearable
                                outlined
                                background-color="grey"
                                v-model="mycomment2"
                                :placeholder="updatingComment.rContent"
                                color="indigo"
                                :counter="100"
                              ></v-textarea>
                            </v-row>
                            <v-row justify="center" class="mt-n5">
                              <v-col cols="6">
                                <v-btn
                                  color="grey"
                                  plain
                                  @click="dialogVisible2 = false"
                                  >취소</v-btn
                                >
                              </v-col>
                              <v-col cols="6">
                                <v-btn
                                  color="error"
                                  plain
                                  @click="updateComment"
                                  >등록</v-btn
                                >
                              </v-col>
                            </v-row>
                          </v-col>
                        </v-row>
                      </v-card>
                    </v-dialog>
                  </v-card>
                </v-row>
              </v-col>
            </v-row>
          </v-col>
        </v-row>
        <v-system-bar window color="grey darken-4" dark class="bar">
        </v-system-bar>
      </v-card>
    </v-row>
  </v-container>
</template>

<script>
import { mapState, mapActions } from "vuex";
export default {
  data() {
    return {
      dialogVisible: false, // 팝업 창의 표시 여부를 제어하는 데이터 속성
      dialogVisible2: false, // 팝업 창의 표시 여부를 제어하는 데이터 속성
      itemsPerPage: 15,
      currentPage: 1,
      currentVideoUrl: "",
      mycomment: "",
      cId: "",
      pof: 0,
      updatingComment: {},
      mycomment2: "",
    };
  },
  name: "CertView",
  beforeCreate() {
    this.$store.dispatch("getAllRecords");
    this.$store.commit("SET_LOGIN_USER");
  },
  components: {},

  computed: {
    ...mapState(["user"]),
    records() {
      return this.$store.state.allrecords;
    },
    comments() {
      return this.$store.state.comments;
    },
    pageCount() {
      return Math.ceil(this.records.length / this.itemsPerPage);
    },
    paginatedData() {
      const start = (this.currentPage - 1) * this.itemsPerPage;
      const end = start + this.itemsPerPage;
      return this.records.slice(start, end);
    },
  },
  methods: {
    ...mapActions(["fetchComments", "deleteComment"]),

    async openCertificationVideo(item) {
      this.currentVideoUrl = `http://localhost:9999/video/${item.cVideo}`; //`http://localhost:9999/video/${cVideo}`
      this.cId = item.cId;
      this.pof = item.pof;
      console.log(this.currentVideoUrl);

      await this.fetchComments(item.cId);
      console.log("Fetching comments for cId:", item.cId);
    },

    addComment() {
      const payload = {
        cId: this.cId,
        uId: this.user.uId,
        rContent: this.mycomment,
      };
      this.$store.dispatch("addComment", payload);
      this.dialog();
    },

    updateComment() {
      this.dialogVisible2 = false;
      this.updatingComment.rContent = this.mycomment2;
      this.$store.dispatch("updateComment", this.updatingComment);
    },

    deleteComment(comment) {
      this.$store.dispatch("deleteComment", comment);
    },

    dialog() {
      this.dialogVisible = !this.dialogVisible;
    },
    dialog2(payload) {
      this.updatingComment = payload;
      this.dialogVisible2 = !this.dialogVisible2;
    },
    addRespect() {},
    certif() {
      const payload = { cId: this.cId, mId: this.user.uId };
      this.$store.dispatch("updateRecord", payload);
    },
  },
};
</script>

<style>
.bg {
  background-color: black;
  background-size: cover;
  background-position: center;
}
</style>
