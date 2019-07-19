package com.wmtc.wmt.appoint.bean;

import com.wmtc.wmt.base.BaseBean;

import java.util.List;

public class EfficacyDetailsBean extends BaseBean {

    public List<DataBean> data;

    public static class DataBean {
        /**
         * id : null
         * functionId : 253
         * functionName : 敏感肌
         * questionId : 389
         * questionName : 红血丝
         * title : 护理方法
         * subtitleList : [{"id":1101456028878114852,"subTitle":"护肤方法","content":"治疗红血丝最重要的是改善面部毛细血管的弹性，恢复血管的正常收缩与舒张功能，降低血管通透性，护肤品不能快速治疗，但至少可以缓解症状、防止症状恶化。\r\n\r\n1、适度清洁：红血丝皮肤的皮肤屏障能力缺损，最好不要继续损伤，建议使用温和的、无刺激成分的洁面产品。\r\n2、坚持防晒：紫外线是红血丝加重的常见原因，保护皮肤免受紫外线的伤害，建议每天使用SPF30+的防晒产品，最好选择无刺激的纯物理防晒，或是防晒效果完备的防晒衣、帽子、墨镜。\r\n3、避免刺激：任何护肤手段都以温和为基础，避免刺激性护肤成分、避免过热过冷的环境、减少使用去角质产品。\r\n4、修复角质层：使用含有脂肪酸的温和油类成分、神经酰胺、积雪草甘等亲肤的修复成分的护肤品，增强皮肤的屏障功能。","pics":[]},{"id":1101456028878114853,"subTitle":"药物治疗","content":"有一些红血丝是和身体其他部位相关的，比如天生的，或者因为某些疾病出现的红血丝，建议先到医院找医生面诊，积极配合治疗，解决病源之后再考虑护肤问题。\r\n\r\n人为的不当护理和刺激问题导致的红血丝，虽然外用抗过敏药能在短时间内缓解症状，但这类药都有时效性，一旦药效过了，红血丝还是会出现，而且处方药可能有副作用甚至本身也会引起过敏症状，建议在医生的指导下使用。","pics":[]}]
         */

        public String id;
        public String functionId;
        public String functionName;
        public String questionId;
        public String questionName;
        public String title;
        public List<SubtitleListBean> subtitleList;

        public static class SubtitleListBean {
            /**
             * id : 1101456028878114852
             * subTitle : 护肤方法
             * content : 治疗红血丝最重要的是改善面部毛细血管的弹性，恢复血管的正常收缩与舒张功能，降低血管通透性，护肤品不能快速治疗，但至少可以缓解症状、防止症状恶化。
             * <p>
             * 1、适度清洁：红血丝皮肤的皮肤屏障能力缺损，最好不要继续损伤，建议使用温和的、无刺激成分的洁面产品。
             * 2、坚持防晒：紫外线是红血丝加重的常见原因，保护皮肤免受紫外线的伤害，建议每天使用SPF30+的防晒产品，最好选择无刺激的纯物理防晒，或是防晒效果完备的防晒衣、帽子、墨镜。
             * 3、避免刺激：任何护肤手段都以温和为基础，避免刺激性护肤成分、避免过热过冷的环境、减少使用去角质产品。
             * 4、修复角质层：使用含有脂肪酸的温和油类成分、神经酰胺、积雪草甘等亲肤的修复成分的护肤品，增强皮肤的屏障功能。
             * pics : []
             */

            public String id;
            public String subTitle;
            public String content;
            public List<?> pics;
            public boolean isSel;
        }
    }
}
