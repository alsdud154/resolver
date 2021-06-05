package kr.co.velnova.resolver.resolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.velnova.resolver.model.GridRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class CustomArgumentResolver implements HandlerMethodArgumentResolver {

    private final ObjectMapper objectMapper;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(GridRequest.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Map<String, Object> convertMap = convertMap(webRequest);

        ParameterizedType parameterizedType = (ParameterizedType) parameter.getGenericParameterType();
        Class<?> subType = (Class<?>) parameterizedType.getActualTypeArguments()[0];

        Map<String, Object> searchParamsMap = convertSearchParams(convertMap, subType);

        Object searchParams = objectMapper.convertValue(searchParamsMap, subType);

        GridRequest<?> gridRequest = (GridRequest<?>) parameter.getParameterType().getDeclaredConstructor().newInstance();

        gridRequest.setCurrentPage(Integer.parseInt((String) convertMap.getOrDefault("currentPage", "1")));
        gridRequest.setRowCount(Integer.parseInt((String) convertMap.getOrDefault("rowCount", "10")));
        gridRequest.setSearchParamsByObject(searchParams);

        return gridRequest;
    }

    private Map<String, Object> convertSearchParams(Map<String, Object> convertMap, Class<?> subType) {
        Map<String, Object> searchParams = new HashMap<>();

        for (Field field : subType.getDeclaredFields()) {
            searchParams.put(field.getName(), convertMap.get(field.getName()));
        }
        return searchParams;
    }

    private Map<String, Object> convertMap(NativeWebRequest webRequest) {
        Map<String, Object> map = new HashMap<>();

        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

        request.getParameterNames().asIterator().forEachRemaining(key -> {
            if (request.getParameterValues(key).length > 1) {
                map.put(key, request.getParameterValues(key));
            } else {
                map.put(key, request.getParameter(key));
            }
        });

        return map;
    }
}
