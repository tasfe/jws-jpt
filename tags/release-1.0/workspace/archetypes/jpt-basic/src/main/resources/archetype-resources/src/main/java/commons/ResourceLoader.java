package ${package}.commons;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

public class ResourceLoader {

	private ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(
			getClass().getClassLoader());

	public Resource[] getResources(String[] files) throws IOException {
		List<Resource> resourceList = new ArrayList<Resource>();
		for (String file : files) {
			Resource[] resources = getResources(file);
			resourceList.addAll(Arrays.asList(resources));
		}
		return resourceList.toArray(new Resource[resourceList.size()]);
	}

	public Resource[] getResources(String locationPattern) throws IOException {
		return resolver.getResources(locationPattern);
	}

}
