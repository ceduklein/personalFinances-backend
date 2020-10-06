package carlosklein.com.senaimypersonalfinancesapi.controller.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostingDTO {

	private Long id;
	
	private String description;
	
	private Integer month;
	
	private Integer year;
	
	private BigDecimal value;
	
	private Long user;
	
	private String type;
	
	private String status;
	
}
