package com.NowakArtur97.WorldOfManga.feature.user;

import com.NowakArtur97.WorldOfManga.enums.MangaInUserListStatus;
import com.NowakArtur97.WorldOfManga.model.Manga;
import com.NowakArtur97.WorldOfManga.model.MangaInUserList;
import com.NowakArtur97.WorldOfManga.model.MangaRating;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user", schema = "world_of_manga")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    @Setter(value = AccessLevel.PRIVATE)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "is_enabled")
    private boolean isEnabled;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH})
    @JoinTable(name = "user_role", schema = "world_of_manga", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private final Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private final Set<MangaRating> mangasRatings = new HashSet<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private final Set<MangaInUserList> mangaList = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH})
    @JoinTable(name = "favourite_manga", schema = "world_of_manga", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "manga_id"))
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private final Set<Manga> favouriteMangas = new HashSet<>();

    void addRole(Role role) {

        this.getRoles().add(role);
    }

    public MangaRating addMangaRating(Manga manga, int rating) {

        MangaRating mangaRating = new MangaRating(manga, this, rating);

        this.getMangasRatings().add(mangaRating);
        manga.getMangasRatings().add(mangaRating);

        return mangaRating;
    }

    public Manga addMangaToFavourites(Manga manga) {

        this.getFavouriteMangas().add(manga);
        manga.getUserWithMangaInFavourites().add(this);

        return manga;
    }

    public Manga removeMangaFromFavourites(Manga manga) {

        this.getFavouriteMangas().remove(manga);

        manga.getUserWithMangaInFavourites().remove(this);

        return manga;
    }

    public MangaInUserList addMangaToList(Manga manga, MangaInUserListStatus status) {

        MangaInUserList mangaInList = new MangaInUserList(manga, this, status);

        this.getMangaList().add(mangaInList);
        manga.getUsersWithMangaInList().add(mangaInList);

        return mangaInList;
    }

    public MangaInUserList removeMangaFromList(MangaInUserList manga) {

        this.getMangaList().remove(manga);
        manga.setUser(null);

        return manga;
    }
}