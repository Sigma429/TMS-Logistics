"use strict";
const common_vendor = require("../../common/vendor.js");
const pages_api_login = require("../api/login.js");
require("../../utils/request.js");
require("../../utils/env.js");
if (!Array) {
  const _easycom_uni_popup2 = common_vendor.resolveComponent("uni-popup");
  _easycom_uni_popup2();
}
const _easycom_uni_popup = () => "../../uni_modules/uni-popup/components/uni-popup/uni-popup.js";
if (!Math) {
  (NavBar + _easycom_uni_popup)();
}
const NavBar = () => "../../components/Navbar/index.js";
const _sfc_main = {
  __name: "index",
  setup(__props) {
    const popup = common_vendor.ref();
    const store = common_vendor.useStore();
    const handleOpen = () => {
      popup.value.open();
    };
    const decryptPhoneNumber = (e) => {
      handleClose();
      console.log("fff");
      common_vendor.wx$1.login({
        success(res) {
          if (e.detail.errMsg === "getPhoneNumber:ok" && e.target.errMsg === "getPhoneNumber:ok") {
            if (!store.state.user.isLoginSuccess)
              return common_vendor.index.showToast({
                title: "请勿重复登录",
                duration: 2e3,
                icon: "none"
              });
            store.commit("user/setIsLoginSuccess", false);
            pages_api_login.login({
              code: res.code,
              phoneCode: e.detail.code
            }).then((res2) => {
              console.log(res2, "----------------");
              common_vendor.index.setStorageSync("token", res2.data.accessToken);
              common_vendor.index.setStorageSync("refreshToken", res2.data.refreshToken);
              common_vendor.index.switchTab({
                url: "/pages/index/index"
              });
              store.commit("user/setIsLoginSuccess", true);
            }).catch((err2) => {
              console.log(err2, "==================");
              common_vendor.index.showToast({
                title: "网络异常",
                duration: 2e3,
                icon: "none"
              });
              store.commit("user/setIsLoginSuccess", true);
            });
          } else {
            console.log(err, "++++++++++++");
            common_vendor.index.redirectTo({
              url: "/pages/login/index"
            });
          }
        }
      });
    };
    const handleClose = () => {
      popup.value.close();
    };
    return (_ctx, _cache) => {
      return {
        a: common_vendor.p({
          title: "登录"
        }),
        b: common_vendor.o(handleOpen),
        c: common_vendor.o(handleClose),
        d: common_vendor.o(handleClose),
        e: common_vendor.o(decryptPhoneNumber),
        f: common_vendor.sr(popup, "d08ef7d4-1", {
          "k": "popup"
        }),
        g: common_vendor.p({
          type: "bottom",
          ["safe-area"]: false
        })
      };
    };
  }
};
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__scopeId", "data-v-d08ef7d4"], ["__file", "D:/Project/express-platform/TMS-Logistics/logistics-user-uniapp-vue3/pages/login/index.vue"]]);
wx.createPage(MiniProgramPage);
