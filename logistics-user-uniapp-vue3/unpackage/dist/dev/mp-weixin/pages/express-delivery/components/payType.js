"use strict";
const common_vendor = require("../../../common/vendor.js");
if (!Array) {
  const _easycom_uni_popup2 = common_vendor.resolveComponent("uni-popup");
  _easycom_uni_popup2();
}
const _easycom_uni_popup = () => "../../../uni_modules/uni-popup/components/uni-popup/uni-popup.js";
if (!Math) {
  _easycom_uni_popup();
}
const _sfc_main = {
  __name: "payType",
  emits: ["@getPayType"],
  setup(__props, { expose, emit: emits }) {
    const popup = common_vendor.ref();
    const payType = common_vendor.ref(1);
    const list = common_vendor.reactive([
      {
        title: "寄付",
        subTitle: "快递员取件时，寄方可在线支付、扫快递员收款码进行支付",
        value: 1
      },
      {
        title: "到付",
        subTitle: "快递签收后，收方可通过扫快递员收款码进行支付",
        value: 2
      }
    ]);
    const handleChangePayType = (item) => {
      payType.value = item.value;
    };
    const handleOpen = () => {
      popup.value.open("bottom");
    };
    const handleCancel = () => {
      popup.value.close("bottom");
      emits("getPayType", payType.value);
    };
    expose({
      handleOpen
    });
    return (_ctx, _cache) => {
      return {
        a: common_vendor.o(handleCancel),
        b: common_vendor.f(list, (item, index, i0) => {
          return {
            a: common_vendor.t(item.title),
            b: common_vendor.t(item.subTitle),
            c: item.value === payType.value ? 1 : "",
            d: index,
            e: common_vendor.o(($event) => handleChangePayType(item), index)
          };
        }),
        c: common_vendor.o(handleCancel),
        d: common_vendor.sr(popup, "2c26eb8d-0", {
          "k": "popup"
        }),
        e: common_vendor.p({
          type: "bottom",
          ["safe-area"]: false
        })
      };
    };
  }
};
const Component = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__scopeId", "data-v-2c26eb8d"], ["__file", "D:/Project/express-platform/TMS-Logistics/logistics-user-uniapp-vue3/pages/express-delivery/components/payType.vue"]]);
wx.createComponent(Component);
