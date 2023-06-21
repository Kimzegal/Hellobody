import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);
const axios = require("axios");
const jose = require("jose");

const REST_API = `http://localhost:9999/api/`;

export default new Vuex.Store({
  state: {
    user: {},
    leagueData: { unload: true },
    exerList: [
      "벤치프레스",
      "데드리프트",
      "스쿼트",
      "오버헤드프레스",
      "바벨컬",
      "레그프레스",
      "풀업",
      "푸시업",
      "3KM",
      "10KM",
      "플랭크",
    ],
    pageData: { user: { rImg: "default.png" } },
    pageLists: {
      list: [
        {
          cDate: "",
          cId: 0,
          cVideo: "",
          exName: "",
          exScore: 0,
          lRate: "",
          mCom: "",
          mId: "",
          pof: 0,
          respect: 0,
          uId: "",
        },
      ],
    },
    allrecords: [],
    comments: [],
    leagueRecords: [],

    cert: {
      scores: [],
      exUnit: "kg",
    },
    maxVal: {
      certed: {},
      unCerted: { exScore: 0 },
    },
  },
  getters: {},
  mutations: {
    ///////////////////////////////////////////////////////////////
    // 리그(지역)에 데이터를 담는다.
    // leagueData = 대지역 : [소지역]
    SET_LEAGUE_DATA(state, payload) {
      state.leagueData = payload.leagues;
    },
    ///////////////////////////////////////////////////////////////
    // 라우터를 이동할 때 마다 실행되는 토큰이용 메서드
    SET_LOGIN_USER(state) {
      const token = localStorage.getItem("login-token");
      if (token) {
        const decoded = jose.decodeJwt(token);
        state.user = decoded;
        console.log("토큰으로부터 유저 정보를 Vuex에 설정했습니다.");
      } else {
        console.log("토큰 정보가 없습니다.");
      }
    },
    ///////////////////////////////////////////////////////////////
    // 리그의 랭킹정보를 가져와서 state에 설정한다.
    SET_RECORDS(state, records) {
      state.leagueRecords = records;
    },
    SET_PAGE_USER(state, payload) {
      console.log(payload);
      state.pageData = payload;
      console.log("개인페이지 정보를 Vuex에 설정했습니다.");
    },
    SET_PAGE_LISTS(state) {
      const token = sessionStorage.getItem("info-token");
      if (token) {
        const decoded = jose.decodeJwt(token);
        console.log(decoded);
        state.pageLists = decoded;
        console.log("토큰으로부터 리스트 정보를 Vuex에 설정했습니다.");
        console.log(decoded);
      } else {
        console.log("리스트에 대한 토큰 정보가 없습니다.");
      }
    },
    LOG_OUT(state) {
      state.user = {};
    },
    //////////////////////////////////////////////////////////////
    // comments 배열에 새로운 댓글 저장
    ADD_COMMENT(state, payload) {
      state.comments.push(payload);
    },

    //////////////////////////////////////////////////////////////
    // comments 배열에 새로운 댓글 저장
    UPDATE_COMMENT(state, payload) {
      console.log(payload);
      for (let i = 0; i < state.comments.length; i++) {
        if (this.comments[i].cId == payload.cId) {
          console.log("내용을 바꿉니다!!");
          this.comments[i].rContent = payload.rContent;
          break;
        }
      }
    },

    SET_ALLRECORDS(state, allrecords) {
      console.log("가져옴!");
      console.log(allrecords);
      state.allrecords = allrecords;
    },
    DELETE_COMMENT(state, com) {
      // commentId를 사용하여 댓글을 삭제하는 로직을 작성합니다.
      // 예시로는 아래와 같이 작성하였습니다.

      const index = state.comments.findIndex(
        (comment) => comment.rId === com.rId
      );
      if (index !== -1) {
        state.comments.splice(index, 1); // 배열에서 해당 인덱스의 댓글을 삭제합니다.
      }
    },

    ///////////////////////////////////////////////////////////////
    // 읽어온 댓글을 comment 객체에 대입한다.
    SET_COMMENTS(state, comments) {
      console.log(comments);
      state.comments.push(...comments);
    },

    SET_CERT_DATA(state, payload) {
      const token = jose.decodeJwt(payload.data["info-token"]);
      console.log("기준 정보를 가져왔습니다.");
      console.log(token);
      state.cert["scores"] = token.cert["score"].split(" ").map(Number);
      state.cert["exUnit"] = token.cert["exUnit"];
    },

    MY_MAX_RECORD(state) {
      console.log("정보변경중");
      state.maxVal["unCerted"] = {};
      state.maxVal["certed"] = {};

      console.log(state.pageLists["list"]);

      for (let record of state.pageLists["list"]) {
        console.log(record);
        let uncerted = false;
        if (!record.default && record.pof == 0 && !uncerted) {
          state.maxVal["unCerted"] = record;
          uncerted = true;
        } else if (record.pof == 1) {
          state.maxVal["certed"] = record;
          break;
        }
      }
    },
  },
  //////////////////////////////////////////////////////////////////////
  // 액션
  actions: {
    ///////////////////////////////////////////////////////////////
    // 리그(지역)에 대한 모든 데이터를 가져온다.
    async getLeagueData({ commit }) {
      const API_URL = `${REST_API}league`;
      await axios({
        url: API_URL,
        method: "GET",
      })
        .then((res) => {
          console.log("리그 데이터를 받아오는 데에 성공했습니다.");
          commit("SET_LEAGUE_DATA", res.data);
        })
        .catch((err) => {
          console.log("리그 데이터를 받아오는 데에 실패했습니다.\n" + err);
          const failure = { "로드 실패": "로드 실패" };
          commit("SET_LEAGUE_DATA", failure);
        });
    },
    ///////////////////////////////////////////////////////////////
    // 유저에 대한 데이터를 가져온다.
    async getUserData({ commit }, payload) {
      const API_URL = `${REST_API}user/${payload.uId}`;
      await axios({
        url: API_URL,
        method: "POST",
        params: payload,
      })
        .then((res) => {
          localStorage.setItem("login-token", res.data["login-token"]);
          console.log("유저 정보를 확인하였습니다.");
          commit("SET_LOGIN_USER");
        })
        .catch((err) => {
          console.log(err);
        });
    },
    ///////////////////////////////////////////////////////////////
    // 토큰을 초기화 시킨다.
    async getNewToken({ commit }) {
      const API_URL = `${REST_API}user/token`;
      await axios({
        url: API_URL,
        method: "POST",
        headers: {
          "login-token": localStorage.getItem("login-token"),
        },
      })
        .then((res) => {
          console.log("토큰을 초기화시킵니다.");
          localStorage.setItem("login-token", res.data["login-token"]);
          commit("SET_LOGIN_USER");
        })
        .catch((err) => {
          console.log(err);
        });
    },
    ///////////////////////////////////////////////////////////////
    // 마이페이지에 대한 정보를 가져온다.
    async getPageInfo({ commit }, payload) {
      const API_URL = `${REST_API}page/${payload.nick}`;
      await axios({
        url: API_URL,
        method: "GET",
        headers: {
          "login-token": localStorage.getItem("login-token"),
        },
      })
        .then((res) => {
          console.log("마이페이지에 필요한 정보를 가져옵니다.");
          commit("SET_PAGE_USER", res.data);
        })
        .catch((err) => {
          console.log(err);
        });
    },

    ///////////////////////////////////////////////////////////////
    // 토큰을 초기화 시킨다.
    async getPageList({ commit }, payload) {
      const API_URL = `${REST_API}myRecordInfo`;
      await axios({
        url: API_URL,
        method: "GET",
        params: payload,
        headers: {
          "login-token": localStorage.getItem("login-token"),
        },
      })
        .then((res) => {
          console.log("페이지에 필요한 레코드 리스트를 가져왔습니다.");
          sessionStorage.setItem("info-token", res.data["info-token"]);
          commit("SET_PAGE_LISTS", res.data);
        })
        .catch((err) => {
          console.log("리스트를 가져오지 못했습니다.");
          console.log(err);
        });
    },
    ///////////////////////////////////////////////////////////////
    // uId와 운동명으로 리그의 랭킹을 가져온다.
    async getLeagueRecords({ commit }, payload) {
      const API_URL = `${REST_API}league/records`;
      await axios({
        url: API_URL,
        method: "GET",
        params: payload,
        headers: {
          "login-token": localStorage.getItem("login-token"),
        },
      })
        .then((res) => {
          console.log("레코드를 성공적으로 가져왔습니다.");
          commit("SET_RECORDS", res.data.records);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    async upload(_, payload) {
      console.log("레코드 업로드를 요청합니다.");
      const API_URL = `${REST_API}uploadRecord`;
      const formData = new FormData();
      console.log(payload.cVideo);
      formData.append("video", payload.cVideo);
      payload.cVideo = null;
      await axios({
        url: API_URL,
        method: "POST",
        params: payload,
        data: formData,
        headers: {
          "Content-Type": "multipart/form-data",
          "login-token": localStorage.getItem("login-token"),
        },
      })
        .then((res) => {
          console.log(res);
          alert("레코드 업로드에 성공하셨습니다.");
        })
        .catch((err) => {
          alert("알수 없는 오류로 업로드에 실패하셨습니다.");
          console.log(err);
        });
    },
    async getAllRecords({ commit }) {
      const API_URL = `${REST_API}allrecords`;
      await axios({
        url: API_URL,
        method: "GET",
        headers: {
          "login-token": localStorage.getItem("login-token"),
        },
      })
        .then((res) => {
          console.log("레코드를 성공적으로 가져왔습니다.");
          commit("SET_ALLRECORDS", res.data);
        })
        .catch((err) => {
          console.log("레코드를 가져오는 중 오류가 발생했습니다.");
          console.log(err);
        });
    },
    getCert({ commit }, requires) {
      const API_URL = `${REST_API}certInfo`;
      axios({
        url: API_URL,
        method: "GET",
        params: requires,
        headers: {
          "login-token": localStorage.getItem("login-token"),
        },
      })
        .then((res) => {
          commit("SET_CERT_DATA", res);
          console.log("운동 정보를 가져왔습니다.");
        })
        .catch((err) => {
          console.log("운동 정보를 가져 오는데 실패했습니다.");
          console.log(err);
        });
    },
    //////////////// 인증해주는 것
    updateRecord(_, selectedUser) {
      const API_URL = `${REST_API}records/cert`;

      axios({
        url: API_URL,
        method: "PUT",
        params: selectedUser,
        headers: {
          "login-token": localStorage.getItem("login-token"),
        },
      })
        .then((response) => {
          if (response.status === 200) {
            alert(response.data.uId + "의 기록을 인증하셨습니다.");
          }
        })
        .catch((error) => {
          console.log(selectedUser.mId);
          console.log("인증 오류가 발생했습니다잉~.");
          console.log(error);
        });
    },
    ///////////////////////////////////////////////////////////////
    // 댓글을 추가한다.
    async addComment({ commit }, commentData) {
      const API_URL = `${REST_API}records/comments`; // REST API의 댓글 추가 엔드포인트
      await axios({
        url: API_URL,
        method: "POST",
        params: commentData,
        headers: {
          "login-token": localStorage.getItem("login-token"),
        },
      })
        .then((res) => {
          if (res.status === 200) {
            console.log("댓글이 성공적으로 추가되었습니다.");
            commit("ADD_COMMENT", res.data);
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },

    ///////////////////////////////////////////////////////////////
    // 댓글을 수정한다.
    async updateComment(_, commentData) {
      const API_URL = `${REST_API}records/comments`; // REST API의 댓글 추가 엔드포인트
      await axios({
        url: API_URL,
        method: "PUT",
        params: commentData,
        headers: {
          "login-token": localStorage.getItem("login-token"),
        },
      })
        .then((res) => {
          if (res.status === 200) {
            console.log("댓글이 성공적으로 변경되었습니다.");
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
    // 댓글을 불러오는 메서드
    async fetchComments({ commit }, cId) {
      const API_URL = `${REST_API}record/comments/${cId}`;
      this.state.comments = [];
      console.log("댓글을 불러옵니다.");
      await axios({
        url: API_URL,
        method: "GET",
        headers: {
          "login-token": localStorage.getItem("login-token"),
        },
      })
        .then((response) => {
          if (response.status === 200) {
            console.log("댓글이 성공적으로 추가되었습니다.");
            commit("SET_COMMENTS", response.data);
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
    ///////////////////////////////////////////////////////////////
    // 댓글을 삭제한다.
    async deleteComment({ commit }, comment) {
      const API_URL = `${REST_API}record/comments/${comment.rId}`;
      await axios({
        url: API_URL,
        method: "DELETE",
        headers: {
          "login-token": localStorage.getItem("login-token"),
        },
      })
        .then((res) => {
          if (res.status === 200) {
            console.log("댓글이 성공적으로 삭제 되었습니다.");
            commit("DELETE_COMMENT", comment);
          }
        })
        .catch((error) => {
          console.error(error);
        });
    },
  },
});
