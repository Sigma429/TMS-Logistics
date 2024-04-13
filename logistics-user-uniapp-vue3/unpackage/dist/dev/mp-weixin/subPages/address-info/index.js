"use strict";
const common_vendor = require("../../common/vendor.js");
const pages_api_address = require("../../pages/api/address.js");
const pages_api_my = require("../../pages/api/my.js");
require("../../utils/request.js");
require("../../utils/env.js");
require("../../pages/api/login.js");
if (!Array) {
  const _component_nav_bar = common_vendor.resolveComponent("nav-bar");
  _component_nav_bar();
}
if (!Math) {
  (BtnFooter + SelectArea)();
}
const BtnFooter = () => "../../components/BtnFooter/index.js";
const SelectArea = () => "./components/selectArea.js";
const _sfc_main = {
  __name: "index",
  setup(__props) {
    const formName = common_vendor.ref("");
    const formPhone = common_vendor.ref("");
    const formAddressInfo = common_vendor.ref("");
    let provinceId = common_vendor.ref("");
    let cityId = common_vendor.ref("");
    let countyId = common_vendor.ref("");
    let title = common_vendor.ref("");
    const isSaveToAddress = common_vendor.ref(false);
    const isDefaultAddress = common_vendor.ref(false);
    const isFromAddress = common_vendor.ref();
    const selectArea = common_vendor.ref();
    const type = common_vendor.ref("send");
    const addressType = common_vendor.ref();
    const editOrAdd = common_vendor.ref();
    const id = common_vendor.ref("");
    const isConfirm = common_vendor.computed(() => {
      return Boolean(formName.value) && Boolean(formPhone.value) && Boolean(formAddressInfo.value) && areaLabel.value !== "城市/地区";
    });
    const isReset = common_vendor.computed(() => {
      return Boolean(formName.value) || Boolean(formPhone.value) || Boolean(formAddressInfo.value) || Boolean(
        provinceId.value
      );
    });
    const areaLabel = common_vendor.ref("城市/地区");
    const isRealNameAuth = common_vendor.ref(true);
    common_vendor.onLoad((options) => {
      if (options.id) {
        getAddressInfo(options.id);
      }
      editOrAdd.value = options.editOrAdd;
      id.value = options.id || "";
      type.value = options.type;
      isFromAddress.value = options.isFromAddress;
      title.value = options.type === "address" ? "编辑地址" : options.type === "send" ? "寄件人地址填写" : "收件人地址填写";
      isDefaultAddress.value = options.isDefault === "0" ? false : true;
    });
    common_vendor.onMounted(() => {
      if (editOrAdd.value === "add") {
        selectArea.value.getList();
      }
      isRealName();
    });
    const toAddress = () => {
      common_vendor.index.navigateTo({
        url: "/pages/address/index?type=" + type.value
      });
    };
    const handleTorealName = () => {
      common_vendor.index.navigateTo({
        url: "/subPages/realName-authentication/index"
      });
    };
    const isRealName = () => {
      pages_api_my.getUserInfo().then((res) => {
        if (res) {
          isRealNameAuth.value = Boolean(res.data.idCardNoVerify);
        }
      });
    };
    const getAddressInfo = async (id2) => {
      await pages_api_address.getAddressInfoDetail(id2).then((res) => {
        const {
          name,
          phoneNumber,
          address,
          city,
          county,
          province,
          isShow,
          isDefault
        } = res.data;
        formName.value = name;
        formPhone.value = phoneNumber;
        formAddressInfo.value = address;
        areaLabel.value = province.name + " " + city.name + " " + county.name;
        provinceId.value = province.id;
        cityId.value = city.id;
        countyId.value = county.id;
        isSaveToAddress.value = isShow;
        isDefaultAddress.value = isDefault;
      });
      selectArea.value.getList();
    };
    const handleSaveToAddress = () => {
      if (isFromAddress.value) {
        isDefaultAddress.value = !isDefaultAddress.value;
      } else {
        isSaveToAddress.value = !isSaveToAddress.value;
      }
    };
    const handledSelectArea = () => {
      selectArea.value.handleOpen();
    };
    const handleFormName = (event) => {
      formName.value = event.detail.value;
    };
    const handleFormPhone = (event) => {
      formPhone.value = event.detail.value;
    };
    const handleFormAddressInfo = (event) => {
      formAddressInfo.value = event.detail.value;
    };
    const getAreaData = (value) => {
      provinceId.value = value.province.id;
      cityId.value = value.city.id;
      countyId.value = value.area.id;
      areaLabel.value = value.province.name + " " + value.city.name + " " + value.area.name;
    };
    const reset = () => {
      formAddressInfo.value = "";
      formName.value = "";
      formPhone.value = "";
      areaLabel.value = "城市/地区";
    };
    const handleToLink = () => {
      common_vendor.index.navigateBack();
    };
    const submit = () => {
      console.log();
      if (!isConfirm.value) {
        common_vendor.index.showToast({
          title: "请将信息填写完整",
          icon: "none"
        });
      } else if (!/^1[3456789]\d{9}$/.test(formPhone.value)) {
        common_vendor.index.showToast({
          title: "请填写正确的手机号码格式",
          icon: "none"
        });
      } else if (formName.value.length < 2) {
        common_vendor.index.showToast({
          title: "姓名字数长度为2-10",
          icon: "none"
        });
      } else {
        if (editOrAdd.value === "add" && !id.value) {
          pages_api_address.addAddress({
            name: formName.value,
            phoneNumber: formPhone.value,
            address: formAddressInfo.value,
            provinceId: provinceId.value,
            cityId: cityId.value,
            countyId: countyId.value,
            type: type.value === "send" ? 1 : 2,
            isShow: isFromAddress.value ? 1 : isSaveToAddress.value ? 1 : 0,
            isDefault: isDefaultAddress.value ? 1 : 0
          }).then((res) => {
            common_vendor.index.showToast({
              title: "操作成功",
              icon: "success"
            });
            if (isFromAddress.value === "true") {
              common_vendor.index.redirectTo({
                url: "/pages/address/index"
              });
            } else {
              common_vendor.index.redirectTo({
                url: "/pages/express-delivery/index?id=" + res.data.id + "&type=" + type.value
              });
            }
          });
        } else if (editOrAdd.value === "edit" && id.value) {
          pages_api_address.editAddress({
            name: formName.value,
            phoneNumber: formPhone.value,
            address: formAddressInfo.value,
            provinceId: provinceId.value,
            cityId: cityId.value,
            countyId: countyId.value,
            type: addressType.value,
            isShow: isFromAddress.value ? 1 : isSaveToAddress.value ? 1 : 0,
            id: id.value,
            isDefault: isDefaultAddress.value ? 1 : 0
          }).then((res) => {
            common_vendor.index.showToast({
              title: "操作成功",
              icon: "success"
            });
            if (isFromAddress.value === "true") {
              common_vendor.index.redirectTo({
                url: "/pages/address/index"
              });
            } else {
              common_vendor.index.redirectTo({
                url: "/pages/express-delivery/index?id=" + res.data.id + "&type=" + type.value
              });
            }
          });
        }
      }
    };
    return (_ctx, _cache) => {
      return common_vendor.e({
        a: common_vendor.p({
          title: common_vendor.unref(title),
          handleToLink
        }),
        b: type.value !== "get" && editOrAdd.value !== "add"
      }, type.value !== "get" && editOrAdd.value !== "add" ? common_vendor.e({
        c: common_vendor.t(!isRealNameAuth.value ? "根据国家法律法规要求，寄件人名称须与实名信息一致。您可以在下单前认证或现场出示证件" : "根据国家法律法规要求，寄件人名称须与实名信息一致。"),
        d: common_vendor.n(type.value === "get" ? "active" : ""),
        e: !isRealNameAuth.value && type.value !== "get"
      }, !isRealNameAuth.value && type.value !== "get" ? {
        f: common_vendor.o(handleTorealName)
      } : {}, {
        g: common_vendor.n(isRealNameAuth.value ? "isRealNameAuth" : "")
      }) : {}, {
        h: type.value === "address"
      }, type.value === "address" ? {} : common_vendor.e({
        i: type.value === "send"
      }, type.value === "send" ? {} : type.value === "get" ? {} : {}, {
        j: type.value === "get",
        k: common_vendor.t(type.value === "send" ? "寄" : "收"),
        l: common_vendor.o(toAddress)
      }), {
        m: common_vendor.n(formName.value !== "" ? "active" : ""),
        n: common_vendor.o(handleFormName),
        o: formName.value,
        p: common_vendor.n(formPhone.value !== "" ? "active" : ""),
        q: common_vendor.o(handleFormPhone),
        r: formPhone.value,
        s: common_vendor.t(areaLabel.value),
        t: common_vendor.n(areaLabel.value !== "城市/地区" ? "active" : ""),
        v: common_vendor.o(handledSelectArea),
        w: common_vendor.n(formAddressInfo.value !== "" ? "active" : ""),
        x: formAddressInfo.value,
        y: common_vendor.o(handleFormAddressInfo),
        z: (isFromAddress.value ? isDefaultAddress.value : isSaveToAddress.value) ? 1 : "",
        A: common_vendor.t(isFromAddress.value ? "设为默认寄件地址" : "保存到地址簿"),
        B: common_vendor.o(handleSaveToAddress),
        C: common_vendor.o(reset),
        D: common_vendor.n(common_vendor.unref(isReset) ? "active" : ""),
        E: common_vendor.o(submit),
        F: common_vendor.p({
          btnText: "确定",
          isActive: common_vendor.unref(isConfirm)
        }),
        G: common_vendor.sr(selectArea, "b08e797b-2", {
          "k": "selectArea"
        }),
        H: common_vendor.o(getAreaData),
        I: common_vendor.p({
          provinceId: common_vendor.unref(provinceId),
          cityId: common_vendor.unref(cityId),
          countyId: common_vendor.unref(countyId),
          editOrAdd: editOrAdd.value
        })
      });
    };
  }
};
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__scopeId", "data-v-b08e797b"], ["__file", "D:/Project/express-platform/TMS-Logistics/logistics-user-uniapp-vue3/subPages/address-info/index.vue"]]);
wx.createPage(MiniProgramPage);
