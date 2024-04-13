"use strict";
const common_vendor = require("../../common/vendor.js");
if (!Array) {
  const _component_nav_bar = common_vendor.resolveComponent("nav-bar");
  _component_nav_bar();
}
if (!Math) {
  (GoodsSeach + WeightAndVolume)();
}
const GoodsSeach = () => "./components/goodsSearch.js";
const WeightAndVolume = () => "./components/weightAndVolume.js";
const _sfc_main = {
  __name: "index",
  setup(__props) {
    const weight = common_vendor.ref(1);
    const volume = common_vendor.ref("");
    const goods = common_vendor.reactive({
      info: {}
    });
    const store = common_vendor.useStore();
    const users = store.state.user;
    const isShow = common_vendor.ref(false);
    let isAlways = common_vendor.ref();
    common_vendor.onMounted(() => {
      goods.info = users.goodsInfo;
    });
    const isActive = common_vendor.computed(() => {
      return Boolean(users.goodsInfo.name);
    });
    const isShowOther = (flag, type) => {
      if (type === "always") {
        isAlways.value = true;
      }
      isShow.value = type === "always" ? true : flag;
    };
    const getWeight = (value) => {
      weight.value = value;
      store.commit("user/setWeight", value);
    };
    const getVolume = (value, long, width, height) => {
      volume.value = value;
      store.commit("user/setLong", long);
      store.commit("user/setWidth", width);
      store.commit("user/setHeight", height);
      store.commit("user/setVolume", value);
    };
    const getGoodsInfo = (value) => {
      goods.info = value;
      store.commit("user/setGoodsInfo", value);
    };
    const confirm = () => {
      if (!goods.info.name) {
        return common_vendor.index.showToast({
          title: "请选择物品",
          duration: 1e3,
          icon: "none"
        });
      }
      common_vendor.index.redirectTo({
        url: "/pages/express-delivery/index?isFromGoods=true"
      });
    };
    return (_ctx, _cache) => {
      return common_vendor.e({
        a: common_vendor.p({
          title: "物品信息"
        }),
        b: common_vendor.o(getGoodsInfo),
        c: common_vendor.p({
          isShowOther
        }),
        d: !isShow.value
      }, !isShow.value ? {
        e: common_vendor.o(getWeight),
        f: common_vendor.o(getVolume)
      } : {}, {
        g: !isShow.value
      }, !isShow.value ? {
        h: common_vendor.n(common_vendor.unref(isActive) ? "active" : ""),
        i: common_vendor.o(confirm)
      } : {}, {
        j: isShow.value ? 1 : ""
      });
    };
  }
};
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__file", "D:/Project/express-platform/TMS-Logistics/logistics-user-uniapp-vue3/pages/goodsInfo/index.vue"]]);
wx.createPage(MiniProgramPage);
