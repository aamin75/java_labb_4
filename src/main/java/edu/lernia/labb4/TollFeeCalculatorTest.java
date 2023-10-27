package edu.lernia.labb4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TollFeeCalculatorTest {
	
	@Test
	@DisplayName("Test total fee cost for normal working day")
	void testGetTotalTollFeeCost() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime[] dates = {
	        LocalDateTime.parse("2023-10-16 06:15", formatter),
	        LocalDateTime.parse("2023-10-16 07:45", formatter),
	        LocalDateTime.parse("2023-10-16 15:10", formatter),
	        LocalDateTime.parse("2023-10-16 18:20", formatter)
	    };
	    int expectedFee = 47;
	    int actualFee = TollFeeCalculator.getTotalFeeCost(dates);
        assertEquals("Total fee cost ",expectedFee, actualFee);
	}

    @Test
	@DisplayName("Test individual passes fees")
    void testGetTollFeePerPassing() {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    	LocalDateTime[] dates = {
	        LocalDateTime.parse("2023-10-16 09:15", formatter),
	        LocalDateTime.parse("2023-10-16 10:15", formatter),
	          	        
        };
        int[] expectedFees = {8, 8};
        for(int i = 0; i < dates.length; i++) {
        	String dateStr = dates[i].format(formatter);
            int actualFees = TollFeeCalculator.getTollFeePerPassing(dates[i]);
            assertEquals(dateStr+" pass ", expectedFees[i], actualFees);
        }
    }
    
    @Test
	@DisplayName("Test fee cost for one pass only")
	void testGetOnePassFeeCost() {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    	LocalDateTime[] dates = {
    	       LocalDateTime.parse("2023-10-16 06:05", formatter)
    	};
    	int expectedFee = 8;
	    int actualFee = TollFeeCalculator.getTotalFeeCost(dates);
	    //String dateStr = dates[].format(formatter);
        assertEquals("One pass only fee ",expectedFee, actualFee);
	}
   
    @Test
	@DisplayName("Test total fee cost should not exceed the daily limit 60kr")
	void testCheckTotalTollFeeCostOverDailyLimit() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime[] dates = {
	        LocalDateTime.parse("2023-10-16 06:30", formatter),
	        LocalDateTime.parse("2023-10-16 07:30", formatter),
	        LocalDateTime.parse("2023-10-16 08:30", formatter),
	        LocalDateTime.parse("2023-10-16 09:30", formatter),
	        LocalDateTime.parse("2023-10-16 10:30", formatter),
	        LocalDateTime.parse("2023-10-16 11:30", formatter),
	        LocalDateTime.parse("2023-10-16 18:20", formatter)
	    };
	    int expectedFee = 60;
	    int actualFee = TollFeeCalculator.getTotalFeeCost(dates);
        assertEquals("Total fee cost should not exceed ",expectedFee, actualFee);
	} 

    @Test
	@DisplayName("Test total fee cost for consecutive passes within one hour")
	void testConsecutivePassesTollFeeWithinOneHour() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime[] dates = {
	        LocalDateTime.parse("2023-10-16 06:15", formatter),
	        LocalDateTime.parse("2023-10-16 06:30", formatter),
	        LocalDateTime.parse("2023-10-16 06:45", formatter),
	        LocalDateTime.parse("2023-10-16 07:00", formatter)
	    };
	    int expectedFee = 18;
	    int actualFee = TollFeeCalculator.getTotalFeeCost(dates);
        assertEquals("Total fee cost for consecutive passes within one hour",expectedFee, actualFee);
	}
    
    @Test
	@DisplayName("Test considering the highest fee for consecutive passes within one hour")
	void testHigherTollFeeForConsecutivePassesWithinOneHour() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime[] dates = {
	        LocalDateTime.parse("2023-10-16 06:15", formatter),
	        LocalDateTime.parse("2023-10-16 06:30", formatter),
	        LocalDateTime.parse("2023-10-16 06:45", formatter)
	    };
	    int expectedFee = 13;
	    int actualFee = TollFeeCalculator.getTotalFeeCost(dates);
        assertEquals("Highest fee for consecutive passes within one hour",expectedFee, actualFee);
	}
    
    @Test
   	@DisplayName("Test total fee cost for null fee hours")
   	void testTollFeeForNullFeeHours() {
   		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
   		LocalDateTime[] dates = {
   	        LocalDateTime.parse("2023-10-16 18:45", formatter),
   	        LocalDateTime.parse("2023-10-16 20:30", formatter),
   	        LocalDateTime.parse("2023-10-17 00:45", formatter)
   	    };
   	    int expectedFee = 0;
   	    int actualFee = TollFeeCalculator.getTotalFeeCost(dates);
           assertEquals("Total fee cost for null fee hours",expectedFee, actualFee);
   	}
    
    @Test
   	@DisplayName("Test toll fee for free days")
   	void testTollFeeForFreeDays() {
   		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
   		LocalDateTime[] dates = {
   	        LocalDateTime.parse("2023-10-15 07:00", formatter),
   	        LocalDateTime.parse("2023-10-15 10:30", formatter),
   	        LocalDateTime.parse("2023-10-15 14:45", formatter)
   	    };
   	    int expectedFee = 0;
   	    int actualFee = TollFeeCalculator.getTotalFeeCost(dates);
           assertEquals("Total fee cost for free days",expectedFee, actualFee);
   	}
    
    @Test
   	@DisplayName("Test toll fee for free month")
   	void testTollFeeForFreeMonth() {
   		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
   		LocalDateTime[] dates = {
   	        LocalDateTime.parse("2023-07-11 07:00", formatter),
   	        LocalDateTime.parse("2023-07-11 10:30", formatter),
   	        LocalDateTime.parse("2023-07-11 14:45", formatter)
   	    };
   	    int expectedFee = 0;
   	    int actualFee = TollFeeCalculator.getTotalFeeCost(dates);
           assertEquals("Total fee cost for free month",expectedFee, actualFee);
   	}
    
    @Test
	@DisplayName("Test certain time fee")
    void testGetTollFeePerCertainPassing() {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    	LocalDateTime date = LocalDateTime.parse("2023-10-16 12:15", formatter);
        int expectedFee = 8;
        int actualFee = TollFeeCalculator.getTollFeePerPassing(date);
        String dateStr = date.format(formatter);
        assertEquals(dateStr+" pass ", expectedFee, actualFee);
    }
    
	/*
	 * @Test
	 * 
	 * @DisplayName("Test free day") void testIsTollFreeDate() { LocalDateTime
	 * tollFreeDate = LocalDateTime.parse("2023-10-15 12:00",
	 * DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
	 * assertTrue(TollFeeCalculator.isTollFreeDate(tollFreeDate)); LocalDateTime
	 * notTollFreeDate = LocalDateTime.parse("2023-10-17 12:00",
	 * DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
	 * assertFalse(TollFeeCalculator.isTollFreeDate(notTollFreeDate)); }
	 */
   
	/*
	 * @Test
	 * 
	 * @DisplayName("Test free month") void testIsTollFreeMonth() { LocalDateTime
	 * tollFreeMonth = LocalDateTime.parse("2023-07-12 12:00",
	 * DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
	 * assertTrue(TollFeeCalculator.isTollFreeDate(tollFreeMonth)); LocalDateTime
	 * notTollFreeMonth = LocalDateTime.parse("2023-10-17 12:00",
	 * DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
	 * assertFalse(TollFeeCalculator.isTollFreeDate(notTollFreeMonth)); }
	 */

}
