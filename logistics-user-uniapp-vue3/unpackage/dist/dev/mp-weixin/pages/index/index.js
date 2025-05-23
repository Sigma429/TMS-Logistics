"use strict";
const common_vendor = require("../../common/vendor.js");
const utils_index = require("../../utils/index.js");
if (!Math) {
  common_vendor.unref(OrderList)();
}
const OrderList = () => "./components/orderList.js";
const _sfc_main = {
  __name: "index",
  setup(__props) {
    const store = common_vendor.useStore();
    store.state.user;
    let orderListRef = common_vendor.ref();
    common_vendor.onShow(() => {
      orderListRef.value && orderListRef.value.indexGetOrderListFunc();
    });
    common_vendor.onPullDownRefresh(() => {
      orderListRef.value.indexGetOrderListFunc();
    });
    const stopRefreshFunc = () => {
      common_vendor.index.stopPullDownRefresh();
    };
    const toExpressDelivery = () => {
      common_vendor.index.navigateTo({
        url: common_vendor.index.getStorageSync("token") ? "/pages/express-delivery/index" : "/pages/login/index"
      });
    };
    return (_ctx, _cache) => {
      return {
        a: common_vendor.o(toExpressDelivery),
        b: common_vendor.o((...args) => common_vendor.unref(utils_index.handleSecondQi) && common_vendor.unref(utils_index.handleSecondQi)(...args)),
        c: common_vendor.o((...args) => common_vendor.unref(utils_index.handleSecondQi) && common_vendor.unref(utils_index.handleSecondQi)(...args)),
        d: common_vendor.o((...args) => common_vendor.unref(utils_index.handleSecondQi) && common_vendor.unref(utils_index.handleSecondQi)(...args)),
        e: common_vendor.sr(orderListRef, "1cf27b2a-0", {
          "k": "orderListRef"
        }),
        f: common_vendor.o(stopRefreshFunc)
      };
    };
  }
};
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__scopeId", "data-v-1cf27b2a"], ["__file", "D:/Project/express-platform/TMS-Logistics/logistics-user-uniapp-vue3/pages/index/index.vue"]]);
wx.createPage(MiniProgramPage);
