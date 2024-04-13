"use strict";
const common_vendor = require("../../common/vendor.js");
const _sfc_main = {
  __name: "index",
  props: {
    btnText: {
      type: String,
      default: "确定"
    },
    isActive: {
      type: Boolean,
      default: false
    }
  },
  emits: ["@confirm"],
  setup(__props, { emit: emits }) {
    const handleClick = () => {
      emits("confirm");
    };
    return (_ctx, _cache) => {
      return {
        a: common_vendor.t(__props.btnText),
        b: common_vendor.n(__props.isActive ? "active" : ""),
        c: common_vendor.o(handleClick)
      };
    };
  }
};
const Component = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__scopeId", "data-v-c2c04913"], ["__file", "D:/Project/express-platform/TMS-Logistics/logistics-user-uniapp-vue3/components/BtnFooter/index.vue"]]);
wx.createComponent(Component);
