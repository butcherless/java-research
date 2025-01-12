package javapocs.extensions.java.util.Collection;

import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

@Extension
public class CollectionExtensions {
    public static <E, R> Stream<R> map(@This Collection<E> thiz, Function<? super E, R> mapper) {
        return thiz.stream().map(mapper);
    }

    public static Boolean isNullOrEmpty(@This Collection<?> thiz) {
        return Objects.isNull(thiz) || thiz.isEmpty();
    }

    public static Boolean isNotEmpty(@This Collection<?> thiz) {
        return !isNullOrEmpty(thiz);
    }

}