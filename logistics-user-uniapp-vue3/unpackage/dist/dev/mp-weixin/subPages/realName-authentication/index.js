"use strict";
const common_vendor = require("../../common/vendor.js");
const utils_validate = require("../../utils/validate.js");
const pages_api_my = require("../../pages/api/my.js");
require("../../utils/request.js");
require("../../utils/env.js");
require("../../pages/api/login.js");
if (!Array) {
  const _component_nav_bar = common_vendor.resolveComponent("nav-bar");
  _component_nav_bar();
}
const _sfc_main = {
  __name: "index",
  setup(__props) {
    let namePlaceholder = common_vendor.ref("请填写");
    let idcardPlaceholder = common_vendor.ref("请填写");
    let showClearIconName = common_vendor.ref(false);
    let showClearIcon = common_vendor.ref(false);
    let nameInputValue = common_vendor.ref("");
    let inputClearValue = common_vendor.ref("");
    const isCanAuth = common_vendor.computed(() => {
      return utils_validate.validateIdentityCard(inputClearValue.value) && nameInputValue.value;
    });
    const clearInput = (event) => {
      inputClearValue.value = event.detail.value;
      if (event.detail.value.length > 0) {
        showClearIcon.value = true;
      } else {
        showClearIcon.value = false;
        idcardPlaceholder.value = "请填写";
      }
    };
    const clearInputName = (event) => {
      nameInputValue.value = event.detail.value;
      if (event.detail.value.length > 0) {
        showClearIconName.value = true;
      } else {
        showClearIconName.value = false;
        namePlaceholder.value = "请填写";
      }
    };
    const clearIconName = () => {
      nameInputValue.value = "";
      showClearIconName.value = false;
      namePlaceholder.value = "请填写";
      if (!inputClearValue.value) {
        idcardPlaceholder.value = "请填写";
      }
    };
    const clearIcon = () => {
      inputClearValue.value = "";
      showClearIcon.value = false;
      idcardPlaceholder.value = "请填写";
      if (!nameInputValue.value) {
        namePlaceholder.value = "请填写";
      }
    };
    const doAuth = () => {
      if (!isCanAuth.value) {
        return common_vendor.index.showToast({
          title: "信息填写不完整",
          icon: "none",
          duration: 1e3
        });
      }
      pages_api_my.getRealNameStatusApi({
        flag: 1,
        idCard: inputClearValue.value,
        name: nameInputValue.value
      }).then((res) => {
        if (res.code !== 200) {
          common_vendor.index.showToast({
            title: res.msg,
            icon: "none",
            duration: 1e3
          });
        } else {
          common_vendor.index.showToast({
            title: "认证成功",
            icon: "none",
            duration: 1e3
          });
          setTimeout(() => {
            common_vendor.index.switchTab({
              url: "/pages/my/index"
            });
          }, 2e3);
        }
      }).catch(() => {
        common_vendor.index.showToast({
          title: "网络异常",
          duration: 2e3,
          icon: "none"
        });
      });
    };
    return (_ctx, _cache) => {
      return common_vendor.e({
        a: common_vendor.p({
          title: "实名认证"
        }),
        b: common_vendor.o(clearInputName),
        c: common_vendor.unref(nameInputValue),
        d: common_vendor.unref(namePlaceholder),
        e: common_vendor.unref(showClearIconName)
      }, common_vendor.unref(showClearIconName) ? {
        f: common_vendor.o(clearIconName)
      } : {}, {
        g: common_vendor.o(clearInput),
        h: common_vendor.unref(inputClearValue),
        i: common_vendor.unref(idcardPlaceholder),
        j: common_vendor.unref(showClearIcon)
      }, common_vendor.unref(showClearIcon) ? {
        k: common_vendor.o(clearIcon)
      } : {}, {
        l: common_vendor.n(common_vendor.unref(isCanAuth) ? "active" : ""),
        m: common_vendor.o(doAuth)
      });
    };
  }
};
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__scopeId", "data-v-c8907c37"], ["__file", "D:/Project/express-platform/TMS-Logistics/logistics-user-uniapp-vue3/subPages/realName-authentication/index.vue"]]);
wx.createPage(MiniProgramPage);
