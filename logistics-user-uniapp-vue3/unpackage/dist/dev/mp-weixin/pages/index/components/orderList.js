"use strict";
const common_vendor = require("../../../common/vendor.js");
const pages_api_order = require("../../api/order.js");
const utils_index = require("../../../utils/index.js");
require("../../../utils/request.js");
require("../../../utils/env.js");
require("../../api/login.js");
if (!Array) {
  const _easycom_uni_popup_dialog2 = common_vendor.resolveComponent("uni-popup-dialog");
  const _easycom_uni_popup2 = common_vendor.resolveComponent("uni-popup");
  (_easycom_uni_popup_dialog2 + _easycom_uni_popup2)();
}
const _easycom_uni_popup_dialog = () => "../../../uni_modules/uni-popup/components/uni-popup-dialog/uni-popup-dialog.js";
const _easycom_uni_popup = () => "../../../uni_modules/uni-popup/components/uni-popup/uni-popup.js";
if (!Math) {
  (_easycom_uni_popup_dialog + _easycom_uni_popup)();
}
const _sfc_main = {
  __name: "orderList",
  emits: ["@stopRefresh"],
  setup(__props, { expose, emit: emits }) {
    const store = common_vendor.useStore();
    let pageInfo = common_vendor.reactive({
      page: 1,
      pageSize: 10
    });
    let status = common_vendor.ref("more");
    common_vendor.ref(0);
    let allOrderList = common_vendor.reactive({
      data: []
    });
    let orderId = common_vendor.ref();
    let isLogin = common_vendor.ref("");
    let popup = common_vendor.ref(null);
    const close = () => {
      popup.value.close();
      orderId.value = "";
    };
    common_vendor.onShow((options) => {
      isLogin.value = common_vendor.index.getStorageSync("token");
      console.log(!isLogin, !allOrderList.data.length && isLogin.value, "6666");
    });
    common_vendor.onMounted(() => {
      getOrderListFunc();
    });
    const handleCopy = (value) => {
      common_vendor.index.setClipboardData({
        data: value,
        showToast: false,
        success: () => {
          common_vendor.index.hideToast();
          common_vendor.index.hideKeyboard();
          common_vendor.index.showToast({
            title: "复制成功",
            icon: "success",
            duration: 1e3
          });
        }
      });
    };
    const confirm = () => {
      popup.value.close();
      pages_api_order.deleteOrder(orderId.value).then((res) => {
        pageInfo.page = 1;
        pageInfo.pageSize = 10;
        getOrderListFunc();
        common_vendor.index.showToast({
          title: "删除成功",
          icon: "success",
          duration: 1e3
        });
      }).catch((err) => {
        common_vendor.index.showToast({
          title: "网络异常",
          duration: 2e3,
          icon: "none"
        });
      });
    };
    const handleOrderDelete = (id) => {
      orderId.value = id;
      popup.value.open();
    };
    const handleOrderCancel = (id) => {
      common_vendor.index.navigateTo({
        url: "/subPages/order-cancel/index?orderId=" + id
      });
    };
    const toLogin = () => {
      common_vendor.index.navigateTo({
        url: "/pages/login/index"
      });
    };
    const getOrderListFunc = (flag) => {
      status.value = "loading";
      pages_api_order.getOrderList({
        page: pageInfo.page,
        pageSize: pageInfo.pageSize
      }).then((res) => {
        console.log(res, "getOrderListFunc");
        if (res.data) {
          allOrderList.data = res.data.items ? res.data.items.slice(0, 3) : [];
        }
      });
    };
    const handleSeeMore = () => {
      common_vendor.index.switchTab({
        url: "/pages/pickup/index"
      });
    };
    const showOrderStatus = (status2) => {
      switch (status2) {
        case 21e3:
          return "待支付";
        case 23e3:
          return "待取件";
        case 230011:
          return "已取消";
        case 23001:
          return "已取件";
        case 23005:
          return "运送中";
        case 22e3:
          return "已关闭";
        case 23008:
          return "派送中";
        case 23009:
          return "已签收";
        case 23010:
          return "已拒收";
      }
    };
    const handleToOrderInfo = (event, id, transportOrderId) => {
      common_vendor.index.navigateTo({
        url: "/subPages/order-info/index?orderId=" + id + "&transportOrderId=" + transportOrderId
      });
      store.commit("user/setIsToOrderInfo", true);
    };
    const LoadMoreCustomers = () => {
      if (status.value === "no-more") {
        return;
      }
      pageInfo.page = pageInfo.page + 1;
      getOrderListFunc();
    };
    const indexGetOrderListFunc = () => {
      pageInfo.page = 1;
      getOrderListFunc();
    };
    expose({
      indexGetOrderListFunc,
      LoadMoreCustomers
    });
    return (_ctx, _cache) => {
      return common_vendor.e({
        a: !common_vendor.unref(isLogin)
      }, !common_vendor.unref(isLogin) ? {
        b: common_vendor.o(toLogin)
      } : !common_vendor.unref(allOrderList).data.length && common_vendor.unref(isLogin) ? {} : {
        d: common_vendor.f(common_vendor.unref(allOrderList).data, (item, index, i0) => {
          return common_vendor.e({
            a: common_vendor.t([23e3, 22e3, 230011].includes(item.status) ? "订" : "运"),
            b: common_vendor.t([23e3, 22e3, 230011].includes(item.status) ? item.id : item.transportOrderId),
            c: common_vendor.o(($event) => handleCopy([23e3, 22e3, 230011].includes(item.status) ? item.id : item.transportOrderId), index),
            d: common_vendor.t(item.senderCity.name),
            e: common_vendor.t(item.senderName),
            f: common_vendor.t(showOrderStatus(item.status)),
            g: common_vendor.n([21e3, 23e3, 23001, 23005, 23008].includes(item.status) ? "green-arrow" : ""),
            h: common_vendor.n([23009, 23010].includes(item.status) ? "red-arrow" : ""),
            i: common_vendor.n([230011, 22e3].includes(item.status) ? "gray-arrow" : ""),
            j: common_vendor.t(item.receiverCity.name),
            k: common_vendor.t(item.receiverName),
            l: item.status === 23001 && item.transportOrderPointVOS
          }, item.status === 23001 && item.transportOrderPointVOS ? {
            m: common_vendor.t(item.transportOrderPointVOS.length > 0 ? item.transportOrderPointVOS[item.transportOrderPointVOS.length - 1].info : "")
          } : {}, {
            n: [23005, 23008].includes(item.status) && item.transportOrderPointVOS
          }, [23005, 23008].includes(item.status) && item.transportOrderPointVOS ? {
            o: common_vendor.t(item.transportOrderPointVOS.length > 0 ? item.transportOrderPointVOS[item.transportOrderPointVOS.length - 1].info : "")
          } : {}, {
            p: item.status === 23009 && item.transportOrderPointVOS
          }, item.status === 23009 && item.transportOrderPointVOS ? {
            q: common_vendor.t(item.transportOrderPointVOS.length > 0 ? item.transportOrderPointVOS[item.transportOrderPointVOS.length - 1].info : "")
          } : {}, {
            r: item.status === 23010 && item.transportOrderPointVOS
          }, item.status === 23010 && item.transportOrderPointVOS ? {
            s: common_vendor.t(item.transportOrderPointVOS.length > 0 ? item.transportOrderPointVOS[item.transportOrderPointVOS.length - 1].info : "")
          } : {}, {
            t: item.status === 23e3
          }, item.status === 23e3 ? {
            v: common_vendor.t(item.estimatedStartTime)
          } : {}, {
            w: item.status === 230011
          }, item.status === 230011 ? {
            x: common_vendor.t(item.updated)
          } : {}, {
            y: [23001, 23005, 23008, 23010].includes(item.status)
          }, [23001, 23005, 23008, 23010].includes(item.status) ? {
            z: common_vendor.t(item.estimatedArrivalTime)
          } : {}, {
            A: item.status === 22e3
          }, item.status === 22e3 ? {
            B: common_vendor.t(item.updated)
          } : {}, {
            C: item.status === 23009
          }, item.status === 23009 ? {
            D: common_vendor.t(item.updated)
          } : {}, {
            E: [23e3, 23001, 23005, 23008, 23010].includes(item.status)
          }, [23e3, 23001, 23005, 23008, 23010].includes(item.status) ? {} : {}, {
            F: item.status === 23e3
          }, item.status === 23e3 ? {
            G: common_vendor.o(($event) => handleOrderCancel(item.id), index)
          } : {}, {
            H: [22e3, 230011, 23009].includes(item.status)
          }, [22e3, 230011, 23009].includes(item.status) ? {
            I: common_vendor.o(($event) => handleOrderDelete(item.id), index)
          } : {}, {
            J: common_vendor.o((...args) => common_vendor.unref(utils_index.handleSecondQi) && common_vendor.unref(utils_index.handleSecondQi)(...args), index),
            K: item.paymentStatus && [23001, 23005, 23008, 23009, 23010].includes(item.status)
          }, item.paymentStatus && [23001, 23005, 23008, 23009, 23010].includes(item.status) ? {
            L: item.paymentStatus === 1 ? "../../../static/daizhifu.png" : "../../../static/yizhifu.png"
          } : {}, {
            M: index,
            N: common_vendor.o(($event) => handleToOrderInfo(_ctx.event, item.id, item.transportOrderId), index)
          });
        }),
        e: common_vendor.o(handleSeeMore)
      }, {
        c: !common_vendor.unref(allOrderList).data.length && common_vendor.unref(isLogin),
        f: common_vendor.o(close),
        g: common_vendor.o(confirm),
        h: common_vendor.p({
          mode: "base",
          content: "确定是否删除此条订单？",
          animation: false,
          ["before-close"]: true
        }),
        i: common_vendor.sr(popup, "584c4a6f-0", {
          "k": "popup"
        }),
        j: common_vendor.p({
          type: "dialog"
        })
      });
    };
  }
};
const Component = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__scopeId", "data-v-584c4a6f"], ["__file", "D:/Project/express-platform/TMS-Logistics/logistics-user-uniapp-vue3/pages/index/components/orderList.vue"]]);
wx.createComponent(Component);
