<template>
  <v-container fluid fill-height class="bg">
    <v-row justify="center">
      <v-row justify="center">
        <v-card color="black" tile width="1000" height="100"> </v-card>
      </v-row>
      <v-card color="black" tile width="70%" height="auto">
        <v-system-bar window color="grey darken-4" dark>
          <v-icon>mdi-message</v-icon>
          <span>League Ranking</span>
          <v-spacer></v-spacer>
          <v-icon>mdi-checkbox-blank-outline</v-icon>
        </v-system-bar>
        <v-row justify="center" class="rank-table">
          <v-col cols="12">
            <v-row>
              <v-col cols="4">
                <v-carousel
                  :show-arrows="false"
                  hide-delimiters
                  cycle
                  height="800"
                >
                  <v-carousel-item
                    v-for="(item, i) in items"
                    :key="i"
                    :src="item.src"
                  ></v-carousel-item>
                </v-carousel>
              </v-col>
              <v-col cols="8">
                <v-row class="mt-2 mb-n5">
                  <v-col cols="4">
                    <v-select
                      v-model="selectedRegion"
                      :items="regions"
                      label="Region"
                      background-color="grey darken-3"
                      outlined
                      dark
                    ></v-select>
                  </v-col>
                  <v-col cols="4">
                    <v-select
                      v-model="selectedArea"
                      :items="areas"
                      label="Area"
                      background-color="grey darken-3"
                      outlined
                      dark
                    ></v-select>
                  </v-col>
                  <v-col cols="4">
                    <v-select
                      v-model="selectedExercise"
                      :items="exerList"
                      label="Exercise"
                      background-color="grey darken-3"
                      outlined
                      dark
                      @change="checkRank"
                    ></v-select>
                  </v-col>
                </v-row>
                <v-system-bar window color="grey darken-4" class="mb-4" dark>
                  <v-icon>mdi-chevron-triple-up</v-icon>
                  <span
                    >{{ selectedRegion }} {{ selectedArea }}
                    {{ selectedExercise }} 랭킹</span
                  >
                  <v-spacer></v-spacer>
                  <v-icon>mdi-checkbox-blank-outline</v-icon>
                </v-system-bar>
                <v-row justify="center">
                  <v-sheet width="100%" class="mx-3" tile dark>
                    <v-simple-table fixed-header height="665px">
                      <template v-slot:default>
                        <thead>
                          <tr>
                            <th class="text-center">Rank</th>
                            <th class="text-center">ID</th>
                            <th class="text-center">Score</th>
                          </tr>
                        </thead>
                        <tbody>
                          <tr v-for="item in filteredRecords" :key="item.name">
                            <td>{{ item.rank }}</td>
                            <td>{{ item.uId }}</td>
                            <td>{{ item.exScore }}</td>
                            <td v-if="isMaster && item.pof === 0">
                              <v-icon @click="openDialog(item)"
                                >mdi-pencil</v-icon
                              >
                            </td>
                          </tr>
                        </tbody>
                      </template>
                    </v-simple-table>
                  </v-sheet>
                </v-row>
              </v-col>
            </v-row>
          </v-col>
        </v-row>
        <v-system-bar window color="grey darken-4" dark> </v-system-bar>
      </v-card>
    </v-row>
  </v-container>
</template>

<script>
// @ is an alias to /src
import { mapState } from "vuex";

export default {
  data() {
    return {
      selectedRegion: "", // 선택된 필터 옵션
      selectedArea: "", // 선택된 필터 옵션
      selectedExercise: "", // 선택된 필터 옵션
      selectedUser: {},
      items: [
        {
          src: "http://localhost:9999/img/first.jpg",
        },
        {
          src: "http://localhost:9999/img/second.jpg",
        },
        {
          src: "http://localhost:9999/img/third.jpg",
        },
        {
          src: "http://localhost:9999/img/fourth.jpg",
        },
      ],
      // 필터 옵션들
      dialogVisible: false,
    };
  },
  name: "LeagueView",
  beforeCreate() {},
  created() {
    if (this.leagueData["unload"]) this.$store.dispatch("getLeagueData");
  },
  computed: {
    ...mapState(["user", "leagueData", "leagueRecords", "exerList"]),

    regions() {
      return Object.keys(this.leagueData);
    },
    areas() {
      return this.$store.state.leagueData[this.selectedRegion];
    },

    filteredRecords() {
      let filtered = this.leagueRecords;

      filtered = filtered.sort((a, b) => b.exScore - a.exScore); // score 내림차순 정렬
      filtered.forEach((item, index) => {
        item.rank = index + 1; // 순위 할당
      });
      return filtered;
    },
  },
  methods: {
    async updateItem() {
      try {
        // selectedUser의 복사본을 만들고 mId를 null로 설정합니다.
        const userCopy = { ...this.selectedUser };

        // 수정한 레코드를 액션으로 디스패치하여 백엔드로 전송합니다.
        await this.$store.dispatch("updateRecord", userCopy);

        // 필요한 경우 성공 메시지 표시, 리다이렉션 등의 추가 작업을 수행합니다.
      } catch (error) {
        // 오류 처리 로직을 추가할 수 있습니다.
        console.log("인증 오류가 발생했습니다.");
        console.log(error);
      } finally {
        // 다이얼로그를 닫습니다.
        this.dialogVisible = false;
      }
    },

    openDialog(item) {
      this.selectedUser = { ...item, mId: this.user.uId };
      this.dialogVisible = true;
    },
    showRecord() {
      const clickName = this.selectedExercise;
      for (let record of this.maxRecords) {
        if (record.exName == clickName) {
          this.selectedUser = record;
        }
      }
    },
    goMyPage() {
      console.log("ㅎㅇ");
      console.log(this.isMaster);

      if (this.$route.path != `/user/${this.user.nick}`)
        this.$router.replace(`/user/${this.user.nick}`);
    },
    goCertPage() {
      if (this.$route.path != "/cert") this.$router.replace("/cert");
    },
    checkRank() {
      const payload = {
        region: this.selectedRegion,
        area: this.selectedArea,
        exName: this.selectedExercise,
      };

      this.$store.dispatch("getLeagueRecords", payload);
    },
  },
  components: {},
};
</script>

<style scoped>
.bg {
  background-color: black;
  background-size: cover;
  background-position: center;
}
.filter-header {
  text-align: center; /* 가운데 정렬 설정 */
}
.filter-summary {
  text-align: center; /* 가운데 정렬 설정 */
}

.divider-wrapper {
  height: 120%; /* 구분선의 높이를 설정 */
}
.board {
  padding-top: 200px;
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
}

.board-item {
  width: 200px;
  margin-bottom: 20px;
  padding: 20px;
  background-color: #f0f0f0;
  text-align: center; /* 가운데 정렬 설정 */
}
</style>
