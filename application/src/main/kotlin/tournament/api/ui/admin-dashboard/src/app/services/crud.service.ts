import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders, HttpParams} from '@angular/common/http';
import {catchError} from 'rxjs/operators';
import {BehaviorSubject, Observable, throwError} from 'rxjs';
import {AbstractControl} from '@angular/forms';


export interface Player {
  pseudo: string;
  points: number;
  ranking: number;
}

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
  })
};

@Injectable({
  providedIn: 'root'
})
export class CrudService {

  constructor(private http: HttpClient) {
  }

  private readonly localApiUrl = 'http://localhost:8080/players';
  private static logError(error: HttpErrorResponse): void {
    if (error.error instanceof ErrorEvent) {
      // A client-side or network error occurred. Handle it accordingly.
      console.log('An error occurred:', error.error.message);
    } else {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong.
      console.log(
        `Backend returned code ${error.status}, ` +
        `body was: ${error.error}`);
    }
  }

  AddPlayer(player: Player): Observable<Player> {
    return this.http.post<Player>(`${this.localApiUrl}?pseudo=${player.pseudo}`, httpOptions )
      .pipe(
        catchError(this.handleError)
      );
  }

  GetPlayer(pseudo: string): Observable<Player> {
    return this.http.get<Player>(`${this.localApiUrl}/${pseudo}`, httpOptions).pipe(
      catchError(this.handleError)
    );
  }

  GetPlayersList(): Observable<any> {
    return this.http.get<Player[]>(this.localApiUrl, httpOptions);
  }


  UpdatePlayerPoints(pseudo: string, player: Player): Observable<boolean> {
    return this.http.patch<boolean>(`${this.localApiUrl}/${pseudo}`, JSON.stringify(player), httpOptions)
      .pipe(
        catchError(this.handleBooleanError)
      );
  }

  private handleError(error: HttpErrorResponse): Observable<Player> {
    CrudService.logError(error);
    // Return an observable with a user-facing error message.
    return throwError(
      'Something bad happened; please try again later.');
  }

  private handleBooleanError(error: HttpErrorResponse): Observable<boolean> {
    CrudService.logError(error);

    // Return an observable with a user-facing error message.
    return new BehaviorSubject<boolean>(false);
  }


}
