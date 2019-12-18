//package github.sjroom.base.context.servlet;
//
//import github.sjroom.core.utils2.UtilJson;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.ObjectProvider;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
//import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.Ordered;
//import org.springframework.http.converter.HttpMessageConverter;
//import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
//
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//import java.util.function.Supplier;
//
///**
// *
// */
//@Slf4j
//@Configuration
//public class DopServletConfiguration {
//
//	@Bean
//	public MappingJackson2HttpMessageConverter getMappingJackson2HttpMessageConverter() {
//		// suppress to HttpMediaTypeNotSupportedException
//		MappingJackson2HttpMessageConverter jacksonConverter = new MappingJackson2HttpMessageConverter(UtilJson.mapper);
//
////		ArrayList<MediaType> mediaTypes = new ArrayList<>(jacksonConverter.getSupportedMediaTypes());
////		mediaTypes.add(MediaType.TEXT_HTML);
////		mediaTypes.add(MediaType.TEXT_PLAIN);
////		jacksonConverter.setSupportedMediaTypes(mediaTypes);
//		return jacksonConverter;
//	}
//
//	@Bean
//	@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
//	public FilterRegistrationBean controllerCallContextFilter() {
//		FilterRegistrationBean<ControllerCallContextFilter> registrationBean = new FilterRegistrationBean<>();
//		registrationBean.setFilter(new ControllerCallContextFilter());
//		registrationBean.addUrlPatterns(DopWebMvcConfigurer.ApiMapping);
//		registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
//		return registrationBean;
//	}
//
//
//	@Bean
//	public HttpMessageConverters messageConverters(ObjectProvider<List<HttpMessageConverter<?>>> convertersProvider) {
//		List<HttpMessageConverter<?>> available = convertersProvider.getIfAvailable();
//		HttpMessageConverters tmp = available == null ? new HttpMessageConverters() : new HttpMessageConverters(available);
//		List<HttpMessageConverter<?>> converts = new ArrayList<>(tmp.getConverters());
//		addIfNotExist(converts, MappingJackson2HttpMessageConverter.class, MappingJackson2HttpMessageConverter::new);
//		sort(converts);
//		return new HttpMessageConverters(false, converts);
//	}
//
//	protected void addIfNotExist(List<HttpMessageConverter<?>> converters, Class<?> clazz, Supplier<HttpMessageConverter<?>> supplier) {
//		for (HttpMessageConverter<?> converter : converters) {
//			if (clazz.isInstance(converter)) return;
//		}
//		converters.add(supplier.get());
//	}
//
//	/**
//	 * 将JsonConverter 顺序提前，优先json格式输出
//	 * @param converts
//	 */
//	protected void sort(final List<HttpMessageConverter<?>> converts) {
//		// move MappingJackson2 to before String
//		List<HttpMessageConverter<?>> jacksonConverts = new ArrayList<>();
//		HttpMessageConverter<?> stringConvert = null;
//		for (Iterator<HttpMessageConverter<?>> iter = converts.iterator(); iter.hasNext();) {
//			HttpMessageConverter<?> convert = iter.next();
//			String name = convert.getClass().getSimpleName();
//			if (name.startsWith("MappingJackson2")) {
//				jacksonConverts.add(convert);
//				iter.remove();
//			} else if (stringConvert == null && name.startsWith("String")) {
//				stringConvert = convert;
//			}
//		}
//		if (stringConvert != null && !jacksonConverts.isEmpty()) {
//			converts.addAll(converts.indexOf(stringConvert), jacksonConverts);
//		}
//	}
//
//
//}
