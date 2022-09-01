package com.tnkfactory.rwd.offerer

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.tnkfactory.ad.*
import com.tnkfactory.rwd.offerer.databinding.ActivityCustomBinding

class CustomSampleActivity : AppCompatActivity() {

    var arrAdType: Array<AdListType> = arrayOf(AdListType.ALL, AdListType.PPI, AdListType.CPS)
    var adStyle: TnkLayout = TemplateLayoutUtils.getBlueStyle_01()

    var adView: AdListTabView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityCustomBinding = DataBindingUtil.setContentView(this, R.layout.activity_custom)

        adStyle = initCustomLayout()

        adView = TnkSession.createAdListTabView(this@CustomSampleActivity, "basic", adStyle, *arrAdType)
        binding.flContent.addView(adView)

    }


    fun initCustomLayout(): TnkLayout {
        val layout: TnkLayout = TemplateLayoutUtils.getBlueTabStyle_01()

        layout.adwall.layout = R.layout.com_tnk_listview
        layout.adwall.idList = R.id.com_tnk_offerwall_layout_adlist
        layout.adwall.listDividerHeightIcon = 0

//        layout.adwall.header.layout = 0

        layout.adwall.itemIcon.layout = R.layout.com_tnk_listview_icon
        layout.adwall.itemIcon.idImage = R.id.com_tnk_offerwall_item_image
        layout.adwall.itemIcon.idIcon = R.id.com_tnk_listview_icon
        layout.adwall.itemIcon.idTitle = R.id.com_tnk_offerwall_item_title
        layout.adwall.itemIcon.idSubtitle = R.id.com_tnk_offerwall_item_sub_title
        layout.adwall.itemIcon.idTagUnit = R.id.com_tnk_offerwall_item_tag_unit
        layout.adwall.itemIcon.idTagPoint = R.id.com_tnk_offerwall_item_tag_point
        layout.adwall.itemIcon.idCampnType = R.id.com_tnk_offerwall_item_campn_type
        layout.adwall.itemIcon.campn.bgCampnCPI = 0
        layout.adwall.itemIcon.campn.bgCampnCPS = 0
        layout.adwall.itemIcon.campn.tcCampnCPI = Color.parseColor("#ffffffff")
        layout.adwall.itemIcon.campn.tcCampnCPS = Color.parseColor("#ffffffff")
        layout.adwall.itemIcon.tag.bgTagNoraml = 0
        layout.adwall.itemIcon.tag.bgTagCheck = 0
        layout.adwall.itemIcon.tag.tagCheckFormat = "{point}"
        layout.adwall.itemIcon.tag.tagNormalFormat = "{point}"
        layout.adwall.itemIcon.tag.pointUnitFormat = "{unit}받기"


        layout.adwall.detail.layout = R.layout.com_tnk_detail
        layout.adwall.detail.idHeaderTitle = R.id.com_tnk_offerwall_detail_header_title
        layout.adwall.detail.idCancel = R.id.com_tnk_offerwall_detail_close
        layout.adwall.detail.idContent = R.id.com_tnk_offerwall_detail_content_layout
        layout.adwall.detail.idTitle = R.id.com_tnk_offerwall_detail_title
        layout.adwall.detail.idSubtitle = R.id.com_tnk_offerwall_detail_sub_title
        layout.adwall.detail.idConfirm = R.id.com_tnk_offerwall_detail_confirm
        layout.adwall.detail.idJoinDesc = R.id.com_tnk_offerwall_detail_join_desc
        layout.adwall.detail.idIcon = R.id.com_tnk_offerwall_detail_icon
        layout.adwall.detail.idAppDescSeparator = R.id.com_tnk_offerwall_detail_separator_2
        layout.adwall.detail.idAppDesc = R.id.com_tnk_offerwall_detail_app_desc
        layout.adwall.detail.idDescButton = R.id.com_tnk_offerwall_detail_app_desc_button
        layout.adwall.detail.idActionList = R.id.com_tnk_offerwall_detail_action_items
        layout.adwall.detail.idCampnType = R.id.com_tnk_offerwall_detail_type
        layout.adwall.detail.campn.tcCampnCPI = Color.parseColor("#ffffffff")
        layout.adwall.detail.campn.tcCampnCPS = Color.parseColor("#ffffffff")
        layout.adwall.detail.idPoint = R.id.com_tnk_offerwall_detail_point
        layout.adwall.detail.confirmText = "{unit}받기"
        layout.adwall.detail.confirmTextCPS = "{unit}받기"
        layout.adwall.detail.priceLayout = R.id.com_tnk_offerwall_detail_price_layout
        layout.adwall.detail.idOrgPrice = R.id.com_tnk_offerwall_detail_price_layout_org
        layout.adwall.detail.idPrdPrice = R.id.com_tnk_offerwall_detail_price_layout_prd
        layout.adwall.detail.tag.tagCheckFormat = "{point}"
        layout.adwall.detail.tag.tagNormalFormat = "{point}"
        layout.adwall.detail.tag.pointUnitFormat = "{unit}받기"
        layout.adwall.detail.tag.tcTagCheck = Color.parseColor("#ffffffff")
        layout.adwall.detail.tag.tcTagNormal = Color.parseColor("#ffffffff")
        layout.adwall.detail.tag.tcHeaderTitle = Color.parseColor("#ff000000")
        layout.adwall.detail.tag.bgHeaderTitle = Color.parseColor("#ffffffff")
        layout.adwall.detail.tag.tcConfirm = Color.parseColor("#ffe62d27")
        layout.adwall.detail.tag.bgConfirm = 0
        layout.adwall.detail.tag.tcTagNormal = Color.parseColor("#ff000000")
        layout.adwall.detail.tag.tcTagCheck = Color.parseColor("#ff000000")
        layout.adwall.detail.tag.tcDescButton = Color.parseColor("#ff000000")
        layout.adwall.detail.idHeaderTitleBg = R.id.com_tnk_offerwall_detail_appbar

        return layout
    }

    companion object {
        fun start(context: Context) {
            val it = Intent(context, CustomSampleActivity::class.java)
            context.startActivity(it)
        }
    }

}