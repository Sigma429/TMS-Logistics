"use strict";
const common_vendor = require("../../common/vendor.js");
const pages_api_order = require("../../pages/api/order.js");
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
    const orderId = common_vendor.ref();
    let defaultReason = common_vendor.ref(1);
    common_vendor.onLoad((options) => {
      orderId.value = options.orderId;
    });
    const customerReason = common_vendor.reactive([
      {
        value: 1,
        label: "计划有变，不需要寄了"
      },
      {
        value: 2,
        label: "换个时间再寄"
      },
      {
        value: 3,
        label: "去服务点自寄"
      }
    ]);
    const senderReason = common_vendor.reactive(
      [
        {
          value: 4,
          label: "送达时间不能达到我的要求"
        },
        {
          value: 5,
          label: "运费太贵了"
        },
        {
          value: 6,
          label: "快递员未及时取件"
        },
        {
          value: 7,
          label: "快递员不上门"
        },
        {
          value: 8,
          label: "快递员服务态度差"
        }
      ]
    );
    const handleConfirmCancel = () => {
      pages_api_order.cancelOrder(orderId.value).then(
        (res) => {
          common_vendor.index.showToast({
            title: "取消成功",
            icon: "none",
            success: () => {
            },
            duration: 2e3
          });
          setTimeout(() => {
            common_vendor.index.switchTab({
              url: "/pages/index/index"
            });
          }, 2500);
        }
      ).catch(() => {
        common_vendor.index.showToast({
          title: "网络异常",
          duration: 2e3,
          icon: "none"
        });
      });
    };
    const handleNoCancel = () => {
      common_vendor.index.navigateBack();
    };
    const checkbox = (value) => {
      defaultReason.value = value;
    };
    return (_ctx, _cache) => {
      return {
        a: common_vendor.p({
          title: "取消订单"
        }),
        b: common_vendor.f(customerReason, (item, index, i0) => {
          return {
            a: String(item.value),
            b: item.value === common_vendor.unref(defaultReason),
            c: common_vendor.t(item.label),
            d: index,
            e: common_vendor.o(($event) => checkbox(item.value), index)
          };
        }),
        c: common_vendor.f(senderReason, (item, index, i0) => {
          return {
            a: String(item.value),
            b: item.value === common_vendor.unref(defaultReason),
            c: common_vendor.t(item.label),
            d: index,
            e: common_vendor.o(($event) => checkbox(item.value), index)
          };
        }),
        d: common_vendor.o(handleNoCancel),
        e: common_vendor.o(handleConfirmCancel)
      };
    };
  }
};
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__scopeId", "data-v-8d887a92"], ["__file", "D:/Project/express-platform/TMS-Logistics/logistics-user-uniapp-vue3/subPages/order-cancel/index.vue"]]);
wx.createPage(MiniProgramPage);
