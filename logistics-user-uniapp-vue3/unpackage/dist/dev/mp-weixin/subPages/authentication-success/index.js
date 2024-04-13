"use strict";
const common_vendor = require("../../common/vendor.js");
const pages_api_my = require("../../pages/api/my.js");
require("../../utils/request.js");
require("../../utils/env.js");
require("../../pages/api/login.js");
if (!Array) {
  const _easycom_uni_popup_dialog2 = common_vendor.resolveComponent("uni-popup-dialog");
  const _easycom_uni_popup2 = common_vendor.resolveComponent("uni-popup");
  (_easycom_uni_popup_dialog2 + _easycom_uni_popup2)();
}
const _easycom_uni_popup_dialog = () => "../../uni_modules/uni-popup/components/uni-popup-dialog/uni-popup-dialog.js";
const _easycom_uni_popup = () => "../../uni_modules/uni-popup/components/uni-popup/uni-popup.js";
if (!Math) {
  (_easycom_uni_popup_dialog + _easycom_uni_popup)();
}
const _sfc_main = {
  __name: "index",
  setup(__props) {
    let src = common_vendor.ref("../../static/idcard-goback.png");
    let idCard = common_vendor.ref("");
    let name = common_vendor.ref("");
    let alertDialog = common_vendor.ref("");
    let capsuleTop = common_vendor.ref();
    common_vendor.onLoad((options) => {
      idCard.value = options.idCard || "";
      name.value = options.name;
      common_vendor.index.getSystemInfo({
        success: (res) => {
          capsuleTop.value = common_vendor.index.getMenuButtonBoundingClientRect().top;
        }
      });
    });
    const handleTo = () => {
      common_vendor.index.navigateBack();
    };
    const handleDelete = () => {
      alertDialog.value.open();
    };
    const dialogConfirm = () => {
      pages_api_my.getRealNameStatusApi({
        flag: 0
      }).then((res) => {
        if (res.code !== 200) {
          common_vendor.index.showToast({
            title: res.msg,
            icon: "none",
            duration: 1e3
          });
        } else {
          common_vendor.index.showToast({
            title: "删除成功",
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
    const dialogClose = () => {
      alertDialog.value.close();
    };
    return (_ctx, _cache) => {
      return {
        a: common_vendor.unref(src),
        b: common_vendor.o(handleTo),
        c: common_vendor.unref(capsuleTop) + 9 + "px",
        d: common_vendor.t(common_vendor.unref(name)),
        e: common_vendor.t(common_vendor.unref(idCard)),
        f: common_vendor.o(handleDelete),
        g: common_vendor.o(dialogConfirm),
        h: common_vendor.o(dialogClose),
        i: common_vendor.p({
          type: "info",
          cancelColor: "red",
          cancelText: "取消",
          confirmText: "确定",
          title: "确定删除实名信息吗？",
          content: "删除后，寄件时需出示身份证件供\r\n快递员重新采集身份信息"
        }),
        j: common_vendor.sr(alertDialog, "7df2a3e3-0", {
          "k": "alertDialog"
        }),
        k: common_vendor.p({
          type: "dialog"
        })
      };
    };
  }
};
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__scopeId", "data-v-7df2a3e3"], ["__file", "D:/Project/express-platform/TMS-Logistics/logistics-user-uniapp-vue3/subPages/authentication-success/index.vue"]]);
wx.createPage(MiniProgramPage);
