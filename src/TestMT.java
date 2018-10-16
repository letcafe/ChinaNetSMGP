import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import com.sxit.smgp3.SMGP3;
import com.sxit.smgp3.TSMGP_RESP;
import com.sxit.smgp3.TSMGP_SUBMIT;
import com.sxit.smgp3.TSMGP_TLV;
import com.sxit.smgp3.socket.SMGPSocket;
import com.sxit.util.Common;

public class TestMT {
    /**
     * Logger for this class
     */
    private static final Log log = LogFactory.getLog(TestMT.class);

    public static void main(String args[]) throws Exception {
        log.info(" MT-TEST Start ");
        SMGP3 smgp = new SMGP3();
        // 最后一个参数代表为0是发送短消息
        // 天津电信IP
        int socketID = smgp.SMGP_Connect("123.150.37.164", 8890, "xxx", "123456", (byte) 0);

        log.info("连接ISMG返回值：" + socketID);

        int count = 10;
        while (count < 200000) {
            count += 1;
            if (count % 10 == 0) {
                if (socketID > 0) {
                    int test_ret = smgp.SMGPActiveTest(socketID);
                    log.info("测试链路返回值：" + test_ret);
                    if (test_ret == 0) {
                        TSMGP_SUBMIT submit = new TSMGP_SUBMIT();
                        submit.cMsgType = 6;
                        submit.cNeedReport = 1;
                        submit.cPriority = 0;
                        submit.sServiceID = "20006";
                        submit.sFeeType = "00";
                        submit.sFeeCode = "0";
                        submit.sFixedFee = "0";
                        submit.sValidTime = "";
                        submit.sAtTime = "";

                        submit.sSrcTermID = "1065555";
                        submit.sChargeTermID = "13366730513";


                        submit.sDestTermID = "13366730513";

                        submit.ucMsgFormat = 15;

                        submit.sMsgContent = count + "-厦航短信测试！收到请回电0592-5739246厦航短信测试！收到请回电0592-5739246厦航短信测试！收到请回电0592-5739246厦航短信测试！收到请回电0592-5739246厦航短信测试！收到请回电0592-5739246";//sxit测试短信5555566";

                        submit.sReserve = "";

                        TSMGP_TLV tlv = new TSMGP_TLV();


                        tlv.b_cDestMaskFlag = true;
                        tlv.cDestMaskFlag = 1;

                        tlv.b_cFeeFlag = true;
                        tlv.cFeeFlag = 1;

                        tlv.b_cFeeMaskFlag = true;
                        tlv.cFeeMaskFlag = 1;


                        tlv.b_cMServiceID = true;
                        tlv.cMServiceID = 1;

                        tlv.b_cMsgType = true;
                        tlv.cMsgType = 1;

                        tlv.b_cNodesCount = true;
                        tlv.cNodesCount = 1;

                        tlv.b_cPid = true;
                        tlv.cPid = 1;

                        tlv.b_cPkNumber = true;
                        tlv.cPkNumber = 1;

                        tlv.b_cPkTotal = true;
                        tlv.cPkTotal = 1;

                        tlv.b_cSpDealResult = true;
                        tlv.cSpDealResult = 1;

                        tlv.b_cSpMaskFlag = true;
                        tlv.cSpMaskFlag = 1;

                        tlv.b_cSrcMaskFlag = true;
                        tlv.cSrcMaskFlag = 1;

                        tlv.b_cUdhi = true;
                        tlv.cUdhi = 1;

                        tlv.b_strDestNumberMask = true;
                        tlv.strDestNumberMask = "123";

                        tlv.b_strMsgSrc = true;
                        tlv.strMsgSrc = "测试TLV";

                        tlv.b_strFeeNumberMask = true;
                        tlv.strFeeNumberMask = "232";

                        tlv.b_strLinkId = true;
                        tlv.strLinkId = "";

                        tlv.b_strSrcNumberMask = true;
                        tlv.strSrcNumberMask = "4333435";


                        //submit.tsmgpTlv=tlv;

                        TSMGP_RESP resp = new TSMGP_RESP();
                        int submit_ret = smgp.SMGP_Submit(socketID, submit, resp);

                        log.info("发送MT返回值：" + submit_ret + " --- seqid:" + resp.getLSerial_ID() + " msgid=" + resp.sMsgID);
                    }
                }
            } else {


                log.info("5秒钟都没有发送，发送链路检测包!");
                int test_ret = smgp.SMGPActiveTest(socketID);
                log.info("测试链路返回值：" + test_ret);
            }

            Thread.sleep(5000);

        }

        //smgp.SMGP_Disconnect(socketID);


    }

    private static String bytes2hex(byte[] b) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < b.length; i++)
            sb.append(Common.byte2hex(b[i]) + " ");
        return sb.toString();
    }


}