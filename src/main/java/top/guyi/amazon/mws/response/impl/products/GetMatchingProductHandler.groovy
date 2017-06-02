package top.guyi.amazon.mws.response.impl.report

import com.amazonservices.mws.products.model.GetMatchingProductRequest
import com.amazonservices.mws.products.model.GetMatchingProductResponse
import top.guyi.amazon.mws.response.SuperResponseHandler
import top.guyi.amazon.mws.response.entity.product.Product

/**
 * Created by 古逸 on 2017-05-26.
 */
class GetMatchingProductHandler extends SuperResponseHandler<GetMatchingProductResponse,GetMatchingProductRequest>{

    List<Product> childs = new LinkedList<Product>()

    Product parent

    @Override
    String getXML(GetMatchingProductResponse response) {
        return response.toXML()
    }

    @Override
    List<EntityData> pattern() {
        String xml = this.response().toXML()
        if(xml.contains('ns2:VariationChild')){
            return [
                    EntityData.create(/<MarketplaceId>(.*?)<\/MarketplaceId><ASIN>(.*?)<\/ASIN>/,
                            {line->
                                Product product = new Product()
                                product.marketplaceId = line[1]
                                product.asin = line[2]
                                childs.add(product)
                            })
            ]
        }else if(xml.contains('VariationParent')){
            return [
                    EntityData.create(/<MarketplaceId>(.*?)<\/MarketplaceId><ASIN>(.*?)<\/ASIN>/,
                            {line->
                                parent = new Product()
                                parent.marketplaceId = line[1]
                                parent.asin = line[2]
                            })
            ]
        }
    }
}
