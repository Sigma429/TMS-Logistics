"use strict";
const common_vendor = require("../../common/vendor.js");
if (!Array) {
  const _component_nav_bar = common_vendor.resolveComponent("nav-bar");
  _component_nav_bar();
}
const _sfc_main = {
  __name: "index",
  setup(__props) {
    const topList = common_vendor.reactive([
      {
        title: "重量小体积小",
        subTitle: "按重量计费"
      },
      {
        title: "重量小体积大",
        subTitle: "按体积计费"
      },
      {
        title: "重量大体积大",
        subTitle: "重量体积取高计费"
      }
    ]);
    return (_ctx, _cache) => {
      return {
        a: common_vendor.p({
          title: "计费规则"
        }),
        b: common_vendor.f(topList, (item, index, i0) => {
          return {
            a: common_vendor.t(item.title),
            b: common_vendor.t(item.subTitle),
            c: index
          };
        })
      };
    };
  }
};
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__scopeId", "data-v-6dbbc75d"], ["__file", "D:/Project/express-platform/TMS-Logistics/logistics-user-uniapp-vue3/subPages/account-rules/index.vue"]]);
wx.createPage(MiniProgramPage);
