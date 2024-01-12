package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entities.Playlist;
import com.example.demo.entities.Song;
import com.example.demo.services.ServicePL;
import com.example.demo.services.SongService;



@Controller
public class PlaylistController {
	
	@Autowired
	SongService songService;
	
	@Autowired
	ServicePL servicepl;
	
	@GetMapping("/createPlaylist")
	public String createplaylist(Model model) {
		
		List<Song> songList = songService.fetchAllSongs();
		model.addAttribute("songs", songList);
		return "createPlaylist";
		
	}
	
	@PostMapping("/addPlaylist")
	public String addPlaylist(@ModelAttribute Playlist playlist) {
		
		servicepl.addPlaylist(playlist);
		List<Song> songList = playlist.getSongs();
		for(Song s : songList) {
			s.getPlaylist().add(playlist);
			songService.updateSong(s);
		}
		return "adminhome";
	}
	@GetMapping("/viewPlaylists")
	public String viewPlaylists(Model model) {
		List<Playlist> allPlaylists = servicepl.fetchAllPlaylists();
		model.addAttribute("allPlaylists", allPlaylists);
		return "displayPlaylists";
	}
	

}
