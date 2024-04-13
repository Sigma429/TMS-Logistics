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
  __name: "getTimePicker",
  emits: ["@getTime"],
  setup(__props, { expose, emit: emits }) {
    const popup = common_vendor.ref();
    let scrollTop = common_vendor.ref(0);
    let selectedDay = common_vendor.ref(0);
    let selectedDayLabel = common_vendor.ref("今天");
    let selectedTime = common_vendor.ref(0);
    let selectedTimeLabel = common_vendor.ref();
    const selectDay = common_vendor.reactive(["今天", "明天", "后天"]);
    let todayList = common_vendor.reactive({
      todos: [{
        label: "一小时内",
        value: 1
      }]
    });
    common_vendor.onMounted(() => {
      todayList.todos = [...todayList.todos.concat(
        dateList.filter((item) => item.value > (/* @__PURE__ */ new Date()).getHours())
      )];
    });
    const dateList = common_vendor.reactive(
      // 弹窗中的时间列表
      Array.from({
        length: 11
      }, (v, k) => ({
        label: `${k + 9}:00-${k + 10}:00`,
        value: k + 9
      }))
    );
    const scroll = (e) => {
      scrollTop.value = e.detail.scrollTop;
    };
    const handleSelectTime = (index, item) => {
      selectedTime.value = index;
      selectedTimeLabel.value = item;
      popup.value.close("bottom");
      emits("getTime", {
        selectedDay: selectedDay.value,
        selectedDayLabel: selectedDayLabel.value,
        selectedTime: selectedTime.value,
        selectedTimeLabel: selectedTimeLabel.value
      });
    };
    const handleSelectDay = (index, item) => {
      selectedDay.value = index;
      scrollTop.value = 0;
      selectedTime.value = 0;
      selectedDayLabel.value = item;
    };
    const handleOpen = () => {
      popup.value.open("bottom");
    };
    const handleCancel = () => {
      popup.value.close("bottom");
    };
    expose({
      handleOpen
    });
    return (_ctx, _cache) => {
      return {
        a: common_vendor.o(handleCancel),
        b: common_vendor.f(selectDay, (item, index, i0) => {
          return {
            a: common_vendor.t(item),
            b: common_vendor.n(common_vendor.unref(selectedDay) === index ? "active" : ""),
            c: common_vendor.o(($event) => handleSelectDay(index, item), index),
            d: index
          };
        }),
        c: common_vendor.f(common_vendor.unref(selectedDay) === 0 ? common_vendor.unref(todayList).todos : dateList, (item, index, i0) => {
          return common_vendor.e({
            a: common_vendor.t(item.label),
            b: common_vendor.unref(selectedTime) === item.value
          }, common_vendor.unref(selectedTime) === item.value ? {} : {}, {
            c: common_vendor.n(common_vendor.unref(selectedTime) === item.value ? "active" : ""),
            d: index,
            e: common_vendor.o(($event) => handleSelectTime(item.value, item.label), index)
          });
        }),
        d: common_vendor.unref(scrollTop),
        e: common_vendor.o(scroll),
        f: common_vendor.sr(popup, "83f22e93-0", {
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
const Component = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__scopeId", "data-v-83f22e93"], ["__file", "D:/Project/express-platform/TMS-Logistics/logistics-user-uniapp-vue3/pages/express-delivery/components/getTimePicker.vue"]]);
wx.createComponent(Component);
