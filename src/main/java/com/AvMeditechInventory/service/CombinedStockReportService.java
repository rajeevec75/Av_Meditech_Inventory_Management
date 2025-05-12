package com.AvMeditechInventory.service;

import com.AvMeditechInventory.dtos.Response;
import com.AvMeditechInventory.results.DataResult;
import java.util.List;

/**
 *
 * @author RAJEEV KUMAR - QMM Technologies Private Limited
 */
public interface CombinedStockReportService {

    public DataResult<List<Response>> getStockReportProductWise(Integer pageNumber, Integer pageSize, String productName, String productType, String brand, List<Integer> channelIds);

}
