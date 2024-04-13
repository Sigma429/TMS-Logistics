"use strict";
const pages_api_user = require("../../pages/api/user.js");
const user = {
  namespaced: true,
  // 开启命名空间
  state() {
    return {
      long: "",
      // 长
      width: "",
      //宽
      height: "",
      //高
      volume: "",
      //体积
      goodsInfo: {},
      weight: 1,
      //体重
      expense: 0,
      //运费
      computeWeight: 0,
      //计算重量
      firstWeight: 0,
      //首重价格
      continuousWeight: 0,
      //续重价格
      toDoorTimeLabel: "",
      //期望上门时间标签
      toDoorTime: String((/* @__PURE__ */ new Date()).getFullYear()) + "-" + String((/* @__PURE__ */ new Date()).getMonth() + 1) + "-" + (/* @__PURE__ */ new Date()).getDate() + " " + String((/* @__PURE__ */ new Date()).getHours() + 1) + ":" + String(Number((/* @__PURE__ */ new Date()).getMinutes()) < 10 ? "0" + Number((/* @__PURE__ */ new Date()).getMinutes()) : Number((/* @__PURE__ */ new Date()).getMinutes())) + ":00",
      //期望上门时间的值
      indexList: [],
      //首页运单列表
      isToOrderInfo: false,
      //是否跳转到订单详情（如果操作完回到首页需要更新列表则为false，不需要更新则为true）
      payMethod: 1,
      isLoginSuccess: true
      //是否登录成功（防止学生在第一次登录还在pendding的时候再次重复登录）
    };
  },
  mutations: {
    setIsLoginSuccess(state, provider) {
      state.isLoginSuccess = provider;
    },
    // 定义mutations，用于同步修改状态
    setPayMethod(state, provider) {
      state.payMethod = provider;
    },
    setIsToOrderInfo(state, provider) {
      state.isToOrderInfo = provider;
    },
    setIndexList(state, provider) {
      state.indexList = provider;
    },
    setToDoorTime(state, provider) {
      state.toDoorTime = provider;
    },
    setToDoorTimeLabel(state, provider) {
      state.toDoorTimeLabel = provider;
    },
    //设置运费
    setExpense(state, provider) {
      state.expense = provider;
    },
    //设置计算重量
    setComputeWeight(state, provider) {
      state.computeWeight = provider;
    },
    //设置首重价格
    setFirstWeight(state, provider) {
      state.firstWeight = provider;
    },
    //设置续重价格
    setContinuousWeight(state, provider) {
      state.continuousWeight = provider;
    },
    setGoodsInfo(state, provider) {
      state.goodsInfo = provider;
    },
    // 设置长
    setLong(state, provider) {
      state.long = provider;
    },
    // 设置宽
    setWidth(state, provider) {
      state.width = provider;
    },
    // 设置高
    setHeight(state, provider) {
      state.height = provider;
    },
    //设置体积
    setVolume(state, provider) {
      state.volume = provider;
    },
    //设置重量
    setWeight(state, provider) {
      state.weight = provider;
    }
  },
  actions: {
    // 获取用户信息
    async GetUsersInfo({
      state,
      commit
    }, payload) {
      if (state.token !== "") {
        await pages_api_user.getUserInfo().then((res) => {
          commit("setUserInfo", res.data);
        }).catch((err) => {
        });
      }
    }
  },
  getters: {}
};
exports.user = user;
