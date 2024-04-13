"use strict";
const common_vendor = require("../common/vendor.js");
const handleSecondQi = () => {
  common_vendor.index.showToast({
    title: "程序员哥哥正在实现中",
    icon: "none",
    duration: 1e3
  });
};
const handleTimeToStrTime = (time) => {
  const newTime = time.replace(/-/g, "/");
  return new Date(newTime).getMonth() + 1 + "月" + (new Date(newTime).getDate() + "日") + " " + String(Number(new Date(newTime).getHours()) < 10 ? "0" + Number(new Date(newTime).getHours()) : Number(new Date(newTime).getHours())) + ":" + String(Number(new Date(newTime).getMinutes()) < 10 ? "0" + Number(new Date(newTime).getMinutes()) : Number(new Date(newTime).getMinutes()));
};
exports.handleSecondQi = handleSecondQi;
exports.handleTimeToStrTime = handleTimeToStrTime;
