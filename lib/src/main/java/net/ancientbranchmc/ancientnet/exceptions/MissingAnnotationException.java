package net.ancientbranchmc.ancientnet.exceptions;

import org.apache.commons.lang.NullArgumentException;

public class MissingAnnotationException extends Exception
{
    String annotation;

    public MissingAnnotationException(String annotation, String reason) {
        super(reason);

        if(annotation == null) {
            throw new NullArgumentException("annotation");
        }

        this.annotation = annotation;
    }

    @Override
    public String getMessage() {
        String msg = super.getMessage();

        String o = "The target class is missing an annotation '" + annotation + "'";
        if(msg != null) {
            o += ": " + msg;
        }

        return o;
    }
}
