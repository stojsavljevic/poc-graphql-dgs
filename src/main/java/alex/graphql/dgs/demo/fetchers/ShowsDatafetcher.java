package alex.graphql.dgs.demo.fetchers;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.reactivestreams.Publisher;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.DgsSubscription;
import com.netflix.graphql.dgs.InputArgument;

import alex.graphql.dgs.demo.types.Author;
import alex.graphql.dgs.demo.types.Show;
import reactor.core.publisher.Flux;

@DgsComponent
public class ShowsDatafetcher {

    private final List<Show> shows = List.of(
            new Show("Stranger Things", 2016, "1"),
            new Show("Ozark", 2017, "2"),
            new Show("The Crown", 2016, "3"),
            new Show("Dead to Me", 2019, "4"),
            new Show("Orange is the New Black", 2013, "5")
    );
    
    private final List<Author> authors = List.of(
            new Author("1", "Stephen King"),
            new Author("2", "George Orwell"),
            new Author("3", "Fyodor Dostoevsky"),
            new Author("4", "Franz Kafka"),
            new Author("5", "Agatha Christie")
    );

    @DgsQuery
    public List<Show> shows(@InputArgument String titleFilter) {
        if(titleFilter == null) {
            return shows;
        }

        return shows.stream().filter(s -> s.getTitle().contains(titleFilter)).collect(Collectors.toList());
    }
    
    @DgsSubscription
	public Publisher<Show> showsSub() {
		return Flux.interval(Duration.ofSeconds(0), Duration.ofSeconds(1))
				.map(t -> getRandomShow())
				.delayElements(Duration.ofSeconds(3));
	}
    
    @DgsData(parentType = "Show", field = "author")
    public Author author(DgsDataFetchingEnvironment dfe) {
    	Show show = dfe.getSource();
    	Optional<Author> author = authors.stream().filter(a -> a.getId().equals(show.getAuthorId())).findAny();
    	return author.get();
    }
    
    private Show getRandomShow() {
    	Random rand = new Random();
        return shows.get(rand.nextInt(shows.size()));
    }
}

