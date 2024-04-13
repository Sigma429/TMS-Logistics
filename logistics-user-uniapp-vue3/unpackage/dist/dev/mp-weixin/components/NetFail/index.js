"use strict";
const common_vendor = require("../../common/vendor.js");
const _sfc_main = {
  __name: "index",
  props: {
    handleToRefresh: {
      //用于自定义跳转
      type: Function
    }
  },
  setup(__props) {
    const props = __props;
    const handleTo = () => {
      props.handleToRefresh();
    };
    return (_ctx, _cache) => {
      return {
        a: common_vendor.o(handleTo)
      };
    };
  }
};
const Component = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__scopeId", "data-v-44cb6724"], ["__file", "D:/Project/express-platform/TMS-Logistics/logistics-user-uniapp-vue3/components/NetFail/index.vue"]]);
wx.createComponent(Component);
