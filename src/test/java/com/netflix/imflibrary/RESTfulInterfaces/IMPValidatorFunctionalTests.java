package com.netflix.imflibrary.RESTfulInterfaces;

import com.netflix.imflibrary.st0429_9.AssetMap;
import com.netflix.imflibrary.utils.ErrorLogger;
import com.netflix.imflibrary.utils.FileByteRangeProvider;
import com.netflix.imflibrary.utils.ResourceByteRangeProvider;
import org.testng.Assert;
import org.testng.annotations.Test;
import testUtils.TestHelper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Test(groups = "functional")
public class IMPValidatorFunctionalTests {

    @Test
    public void invalidPKLTest() throws IOException {
        File inputFile = TestHelper.findResourceByPath("TestIMP/Netflix_Sony_Plugfest_2015/PKL_befcd2d4-f35c-45d7-99bb-7f64b51b103c.xml");
        ResourceByteRangeProvider resourceByteRangeProvider = new FileByteRangeProvider(inputFile);
        byte[] bytes = resourceByteRangeProvider.getByteRangeAsBytes(0, resourceByteRangeProvider.getResourceSize()-1);
        PayloadRecord payloadRecord = new PayloadRecord(bytes, PayloadRecord.PayloadAssetType.PackingList, 0L, resourceByteRangeProvider.getResourceSize());
        List<ErrorLogger.ErrorObject>errors = IMPValidator.validatePKL(payloadRecord);
        Assert.assertTrue(errors.size() > 0);
    }

    @Test
    public void validAssetMapTest() throws IOException {
        //File inputFile = TestHelper.findResourceByPath("TestIMP/Netflix_Sony_Plugfest_2015/ASSETMAP.xml");
        File inputFile = TestHelper.findResourceByPath("TestIMP/NYCbCrLT_3840x2160x23.98x10min/ASSETMAP.xml");
        ResourceByteRangeProvider resourceByteRangeProvider = new FileByteRangeProvider(inputFile);
        byte[] bytes = resourceByteRangeProvider.getByteRangeAsBytes(0, resourceByteRangeProvider.getResourceSize()-1);
        PayloadRecord payloadRecord = new PayloadRecord(bytes, PayloadRecord.PayloadAssetType.AssetMap, 0L, resourceByteRangeProvider.getResourceSize());
        List<ErrorLogger.ErrorObject>errors = IMPValidator.validateAssetMap(payloadRecord);
        Assert.assertTrue(errors.size() == 0);
    }

    @Test
    public void validPKLTest() throws IOException {
        File inputFile = TestHelper.findResourceByPath("TestIMP/NYCbCrLT_3840x2160x23.98x10min/PKL_0429fedd-b55d-442a-aa26-2a81ec71ed05.xml");
        ResourceByteRangeProvider resourceByteRangeProvider = new FileByteRangeProvider(inputFile);
        byte[] bytes = resourceByteRangeProvider.getByteRangeAsBytes(0, resourceByteRangeProvider.getResourceSize()-1);
        PayloadRecord payloadRecord = new PayloadRecord(bytes, PayloadRecord.PayloadAssetType.PackingList, 0L, resourceByteRangeProvider.getResourceSize());
        List<ErrorLogger.ErrorObject>errors = IMPValidator.validatePKL(payloadRecord);
        Assert.assertTrue(errors.size() == 0);
    }

    @Test
    public void validPKLAndAssetMapTest() throws IOException {
        File pklInputFile = TestHelper.findResourceByPath("TestIMP/NYCbCrLT_3840x2160x23.98x10min/PKL_0429fedd-b55d-442a-aa26-2a81ec71ed05.xml");
        ResourceByteRangeProvider resourceByteRangeProvider = new FileByteRangeProvider(pklInputFile);
        byte[] bytes = resourceByteRangeProvider.getByteRangeAsBytes(0, resourceByteRangeProvider.getResourceSize()-1);
        PayloadRecord pklPayloadRecord = new PayloadRecord(bytes, PayloadRecord.PayloadAssetType.PackingList, 0L, resourceByteRangeProvider.getResourceSize());
        List<PayloadRecord> pklPayloadRecordList = new ArrayList<>();
        pklPayloadRecordList.add(pklPayloadRecord);

        File assetMapInputFile = TestHelper.findResourceByPath("TestIMP/NYCbCrLT_3840x2160x23.98x10min/ASSETMAP.xml");
        ResourceByteRangeProvider assetMapResourceByteRangeProvider = new FileByteRangeProvider(assetMapInputFile);
        byte[] assetMapBytes = assetMapResourceByteRangeProvider.getByteRangeAsBytes(0, assetMapResourceByteRangeProvider.getResourceSize()-1);
        PayloadRecord assetMapPayloadRecord = new PayloadRecord(assetMapBytes, PayloadRecord.PayloadAssetType.AssetMap, 0L, assetMapResourceByteRangeProvider.getResourceSize());

        List<ErrorLogger.ErrorObject> errors = IMPValidator.validatePKLAndAssetMap(assetMapPayloadRecord, pklPayloadRecordList);
        Assert.assertTrue(errors.size() == 0);
    }

    @Test
    public void validCPLTest() throws IOException {
        File inputFile = TestHelper.findResourceByPath("TestIMP/NYCbCrLT_3840x2160x23.98x10min/CPL_a453b63a-cf4d-454a-8c34-141f560c0100.xml");
        ResourceByteRangeProvider resourceByteRangeProvider = new FileByteRangeProvider(inputFile);
        byte[] bytes = resourceByteRangeProvider.getByteRangeAsBytes(0, resourceByteRangeProvider.getResourceSize()-1);
        PayloadRecord payloadRecord = new PayloadRecord(bytes, PayloadRecord.PayloadAssetType.CompositionPlaylist, 0L, resourceByteRangeProvider.getResourceSize());
        List<ErrorLogger.ErrorObject>errors = IMPValidator.validateCPL(payloadRecord);
        Assert.assertTrue(errors.size() == 0);
    }

    @Test
    public void validateEssencesHeaderPartition() throws IOException {
        List<PayloadRecord> essencesHeaderPartition = new ArrayList<>();

        File inputFile = TestHelper.findResourceByPath("TestIMP/Netflix_Sony_Plugfest_2015/Netflix_Plugfest_Oct2015_ENG20.mxf.hdr");
        ResourceByteRangeProvider resourceByteRangeProvider = new FileByteRangeProvider(inputFile);
        byte[] bytes = resourceByteRangeProvider.getByteRangeAsBytes(0, resourceByteRangeProvider.getResourceSize()-1);
        PayloadRecord payloadRecord = new PayloadRecord(bytes, PayloadRecord.PayloadAssetType.EssencePartition, 0L, resourceByteRangeProvider.getResourceSize());
        essencesHeaderPartition.add(payloadRecord);

        inputFile = TestHelper.findResourceByPath("TestIMP/Netflix_Sony_Plugfest_2015/Netflix_Plugfest_Oct2015_ENG51.mxf.hdr");
        resourceByteRangeProvider = new FileByteRangeProvider(inputFile);
        bytes = resourceByteRangeProvider.getByteRangeAsBytes(0, resourceByteRangeProvider.getResourceSize()-1);
        payloadRecord = new PayloadRecord(bytes, PayloadRecord.PayloadAssetType.EssencePartition, 0L, resourceByteRangeProvider.getResourceSize());
        essencesHeaderPartition.add(payloadRecord);

        inputFile = TestHelper.findResourceByPath("TestIMP/Netflix_Sony_Plugfest_2015/Netflix_Plugfest_Oct2015_LAS20.mxf.hdr");
        resourceByteRangeProvider = new FileByteRangeProvider(inputFile);
        bytes = resourceByteRangeProvider.getByteRangeAsBytes(0, resourceByteRangeProvider.getResourceSize()-1);
        payloadRecord = new PayloadRecord(bytes, PayloadRecord.PayloadAssetType.EssencePartition, 0L, resourceByteRangeProvider.getResourceSize());
        essencesHeaderPartition.add(payloadRecord);

        inputFile = TestHelper.findResourceByPath("TestIMP/Netflix_Sony_Plugfest_2015/Netflix_Plugfest_Oct2015_LAS51.mxf.hdr");
        resourceByteRangeProvider = new FileByteRangeProvider(inputFile);
        bytes = resourceByteRangeProvider.getByteRangeAsBytes(0, resourceByteRangeProvider.getResourceSize()-1);
        payloadRecord = new PayloadRecord(bytes, PayloadRecord.PayloadAssetType.EssencePartition, 0L, resourceByteRangeProvider.getResourceSize());
        essencesHeaderPartition.add(payloadRecord);

        inputFile = TestHelper.findResourceByPath("TestIMP/Netflix_Sony_Plugfest_2015/Netflix_Plugfest_Oct2015.mxf.hdr");
        resourceByteRangeProvider = new FileByteRangeProvider(inputFile);
        bytes = resourceByteRangeProvider.getByteRangeAsBytes(0, resourceByteRangeProvider.getResourceSize()-1);
        payloadRecord = new PayloadRecord(bytes, PayloadRecord.PayloadAssetType.EssencePartition, 0L, resourceByteRangeProvider.getResourceSize());
        essencesHeaderPartition.add(payloadRecord);

        List<ErrorLogger.ErrorObject> errors = IMPValidator.validateIMFEssenceComponentHeaderMetadata(essencesHeaderPartition);
        Assert.assertTrue(errors.size() == 0);
    }

    @Test
    public void cplConformanceTest() throws IOException {
        File inputFile = TestHelper.findResourceByPath("TestIMP/NYCbCrLT_3840x2160x23.98x10min/CPL_a453b63a-cf4d-454a-8c34-141f560c0100.xml");
        ResourceByteRangeProvider resourceByteRangeProvider = new FileByteRangeProvider(inputFile);
        byte[] bytes = resourceByteRangeProvider.getByteRangeAsBytes(0, resourceByteRangeProvider.getResourceSize()-1);
        PayloadRecord payloadRecord = new PayloadRecord(bytes, PayloadRecord.PayloadAssetType.CompositionPlaylist, 0L, resourceByteRangeProvider.getResourceSize());


    }
}
