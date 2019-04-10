package org.sid.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TechnicalErrorException extends RuntimeException{

	private static final long serialVersionUID = -811807278404114373L;
    
    private Long id;

    public TechnicalErrorException() {
        super();
    }

    public TechnicalErrorException(String message) {
        super(message);
    }

    public TechnicalErrorException(Throwable cause) {
        super(cause);
    }
    
    public TechnicalErrorException(String message, Throwable throwable) {
        super(message, throwable);
    }
    
    public TechnicalErrorException(Long id) {
        super(id.toString());
        this.id = id;
    }
}
