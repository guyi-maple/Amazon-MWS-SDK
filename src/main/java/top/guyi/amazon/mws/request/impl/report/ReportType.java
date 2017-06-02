package top.guyi.amazon.mws.request.impl.report;

/**
 * Created by 古逸 on 2017-05-24.
 */
public enum ReportType {

        /**
         * 可售商品报告 （“库存报告”）
         */
        _GET_FLAT_FILE_OPEN_LISTINGS_DATA_,
        /**
         * 可售商品报告
         */
        _GET_MERCHANT_LISTINGS_DATA_BACK_COMPAT_,
        /**
         * 卖家商品报告 （“在售商品报告”）
         */
        _GET_MERCHANT_LISTINGS_DATA_,
        /**
         * 卖家商品报告（精简版） （“可售商品报告精简版”）
         */
        _GET_MERCHANT_LISTINGS_DATA_LITE_,
        /**
         * 卖家商品报告（精简升级版） （“可售商品报告精简升级版”）
         */
        _GET_MERCHANT_LISTINGS_DATA_LITER_,
        /**
         * 已售商品报告
         */
        _GET_CONVERGED_FLAT_FILE_SOLD_LISTINGS_DATA_,
        /**
         * 已取消的商品报告
         */
        _GET_MERCHANT_CANCELLED_LISTINGS_DATA_,
        /**
         * 商品信息质量报告 （“商品信息质量和阻止上传的商品信息报告”）
         */
        _GET_MERCHANT_LISTINGS_DEFECT_DATA_
}
