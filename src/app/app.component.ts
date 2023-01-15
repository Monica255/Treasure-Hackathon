import { Component } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'treasure';
  sideNavStatus: boolean = false;

  postForm: FormGroup;

  constructor(private fb: FormBuilder) {
    this.postForm = fb.group({})
  }

  ngOnInit(): void {
    this.postForm = this.fb.group({
      title: this.fb.control(''),
      info: this.fb.control(''),
      date: this.fb.control(''),
      price: this.fb.control(''),
    })
  }
}


