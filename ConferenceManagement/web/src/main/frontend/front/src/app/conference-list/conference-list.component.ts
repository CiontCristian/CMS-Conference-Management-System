import { Component, OnInit } from '@angular/core';
import {ConferenceService} from '../shared/conference.service';
import {Conference} from '../shared/conference.model';
import {Router} from "@angular/router";

@Component({
  selector: 'app-conference-list',
  templateUrl: './conference-list.component.html',
  styleUrls: ['./conference-list.component.css']
})
export class ConferenceListComponent implements OnInit {
  conferences: Conference[];
  selectedConference: Conference;

  constructor(private conferenceService: ConferenceService,
              private router: Router) { }

  ngOnInit(): void {
    this.conferenceService.getConferences()
      .subscribe(conferences => this.conferences = conferences);

  }

  onSelect(conference: Conference): void{
    this.selectedConference = conference;
  }

  goToConferenceDetails(conferenceID: number): void{
    this.router.navigate(["conference/", conferenceID]);
  }

}
