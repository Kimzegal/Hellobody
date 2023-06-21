import Vue from "vue";
import VueRouter from "vue-router";
import store from "../store";

Vue.use(VueRouter);

const routes = [
  {
    path: "/user/:nick",
    name: "MyView",
    beforeEnter: (to, from, next) => {
      store.commit("SET_LOGIN_USER");
      const nick = store.state.user["nick"];
      if (!nick) {
        next({ name: "LoginView" });
      } else {
        next();
      }
    },
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/MyView.vue"),
  },
  {
    path: "/league",
    name: "LeagueView",
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/LeagueView.vue"),
  },
  {
    path: "/cert",
    name: "CertView",
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/CertView.vue"),
  },
  {
    path: "/login",
    name: "LoginView",
    beforeEnter: (to, from, next) => {
      store.commit("SET_LOGIN_USER");
      const nick = store.state.user["nick"];
      if (nick) {
        next({ name: "MyView", params: { nick } });
      } else {
        next();
      }
    },
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/LoginView.vue"),
  },

  {
    path: "/*",
    beforeEnter: (to, from, next) => {
      store.commit("SET_LOGIN_USER");
      const nick = store.state.user["nick"];
      if (!nick) {
        next({ name: "LoginView" });
      } else {
        next({ name: "MyView", params: { nick } });
      }
    },
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
